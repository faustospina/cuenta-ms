package com.cuenta.cuenta_ms.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.cuenta.cuenta_ms.model.dto.MovimientoDTO;
import com.cuenta.cuenta_ms.model.dto.TipoMovimiento;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import com.cuenta.cuenta_ms.model.mapper.MovimientoMapper;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import com.cuenta.cuenta_ms.repository.MovimientoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceImpTest {

    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private QueueMessagingTemplate queueMessagingTemplate;
    @Mock
    private AmazonSQS amazonSQS;
    @Mock
    private MovimientoMapper movimientoMapper;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MovimientoServiceImp movimientoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdate() {
        Movimiento movimiento = new Movimiento();
        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimiento));
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);
        when(movimientoMapper.toDTO(any())).thenReturn(new MovimientoDTO(1L,LocalDate.now(), TipoMovimiento.DEPOSITO, 100.0, 500.0,1L));

        MovimientoDTO request = new MovimientoDTO(1L,LocalDate.now(), TipoMovimiento.DEPOSITO, 100.0, 500.0,1L);
        ResponseEntity<Object> response = movimientoService.update(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    void testDelete() {
        Movimiento movimiento = new Movimiento();
        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimiento));

        ResponseEntity<Object> response = movimientoService.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(movimientoRepository).delete(movimiento);
    }


    @Test
    void testGetMovimientosByCuenta() {
        List<Movimiento> movimientos = List.of(new Movimiento());
        when(movimientoRepository.findByCuentaId(1L)).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getMovimientosByCuenta(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movimientoRepository).findByCuentaId(1L);
    }
}