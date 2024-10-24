package com.cuenta.cuenta_ms.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.cuenta.cuenta_ms.exception.BusinessException;
import com.cuenta.cuenta_ms.exception.NotFoundException;
import com.cuenta.cuenta_ms.model.dto.MovimientoDTO;
import com.cuenta.cuenta_ms.model.dto.MovimientoSQS;
import com.cuenta.cuenta_ms.model.dto.TipoMovimiento;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import com.cuenta.cuenta_ms.model.mapper.MovimientoMapper;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import com.cuenta.cuenta_ms.repository.MovimientoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoServiceImp implements MovimientoService{

    public static final String NOT_FOUND_MOVIMIENTO = "Not found movimiento ";
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private MovimientoMapper movimientoMapper;

    @Transactional
    private void registrarMovimiento(MovimientoSQS movimientoSQS) {
        Cuenta cuentaOpt = cuentaRepository.findByNumeroCuenta(movimientoSQS.nomeroCuenta()).orElseThrow(()-> new RuntimeException("not count found"));
        double nuevoSaldo = cuentaOpt.getSaldoInicial();

        if (TipoMovimiento.RETIRO.equals(movimientoSQS.tipoMovimiento())) {
            if (nuevoSaldo < movimientoSQS.valor()) {
                throw new BusinessException("No hay saldo suficiente para realizar el retiro.");
            }
            nuevoSaldo -= movimientoSQS.valor();
        } else if (TipoMovimiento.DEPOSITO.equals(movimientoSQS.tipoMovimiento())) {
            nuevoSaldo += movimientoSQS.valor();
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido.");
        }

        cuentaOpt.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuentaOpt);
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDate.now());
        movimiento.setTipoMovimiento(movimientoSQS.tipoMovimiento().name());
        movimiento.setValor(movimientoSQS.valor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuentaId(cuentaOpt.getId());

        movimientoRepository.save(movimiento);
    }


    @Override
    public ResponseEntity<Object> getAll() {
        List<Movimiento> movimiento = movimientoRepository.findAll();
        return new ResponseEntity<>(movimiento.stream().map(movimientoMapper::toDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> update(Long id, MovimientoDTO request) {
        Movimiento movimiento = getMovimiento(id);
        movimiento.setFecha(request.fecha());
        movimiento.setTipoMovimiento(request.tipoMovimiento().name());
        movimiento.setValor(request.valor());
        movimiento.setSaldo(request.saldo());
        return new ResponseEntity<>(movimientoMapper.toDTO(movimientoRepository.save(movimiento)),HttpStatus.OK);
    }

    private Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MOVIMIENTO + id));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        Movimiento movimiento = getMovimiento(id);
        movimientoRepository.delete(movimiento);
        return ResponseEntity.noContent().build();
    }


    @Override
    public void receiveAndProcessAllMessages() {
        boolean messagesRemaining = true;

        while (messagesRemaining) {
            Message<String> receivedMessage = (Message<String>) queueMessagingTemplate.receive(endpoint);

            if (receivedMessage != null) {
                try {
                    String messagePayload = receivedMessage.getPayload();
                    String receiptHandle = getReceiptHandle(receivedMessage);
                    MovimientoSQS movimiento =  objectMapper.readValue(messagePayload, MovimientoSQS.class);
                    registrarMovimiento(movimiento);
                    deleteMessage(receiptHandle);
                    System.out.println("Mensaje procesado: " + movimiento);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                messagesRemaining = false;
            }
        }
        System.out.println("Todos los mensajes fueron procesados.");
    }

    @Override
    public List<Movimiento> getMovimientosByCuenta(Long idCuenta) {
        return movimientoRepository.findByCuentaId(idCuenta);
    }

    private String getReceiptHandle(Message<String> message) {
        MessageHeaderAccessor accessor = new MessageHeaderAccessor(message);
        return (String) accessor.getHeader("ReceiptHandle");
    }

    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(endpoint, receiptHandle);
        amazonSQS.deleteMessage(deleteMessageRequest);
    }



}
