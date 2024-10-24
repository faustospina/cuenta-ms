package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.exception.NotFoundException;
import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import com.cuenta.cuenta_ms.model.mapper.CuentaMapper;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService{

    public static final String NOT_FOUND_CUENTA = "Not found cuenta";
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Override
    public ResponseEntity<Object> createCount(Long idCliente, CuentaDTO cuenta) {
        Cliente cliente = clienteService.findClienteById(idCliente);
        Cuenta cuentaSave = cuentaMapper.toEntity(cuenta);
        cuentaSave.setCliente(cliente);
        return new ResponseEntity<>(cuentaMapper.toDTO(cuentaRepository.save(cuentaSave)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(cuentaRepository.findAll().stream().map(cuentaMapper::toDTO),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateCuenta(Long idCuenta, CuentaDTO cuenta) {
        Cuenta cuentaOut = getCuentaById(idCuenta);
        cuentaOut.setNumeroCuenta(cuenta.numeroCuenta());
        cuentaOut.setSaldoInicial(cuenta.saldoInicial());
        cuentaOut.setEstado(cuenta.estado());
        return new ResponseEntity<>(cuentaMapper.toDTO(cuentaRepository.save(cuentaOut)),HttpStatus.OK);
    }

    private Cuenta getCuentaById(Long idCuenta) {
        return cuentaRepository.findById(idCuenta).orElseThrow(() -> new NotFoundException(NOT_FOUND_CUENTA));
    }

    @Override
    public ResponseEntity<Object> deleteCuenta(Long id) {
        Cuenta cuenta = getCuentaById(id);
        List<Movimiento> movimientoList = movimientoService.getMovimientosByCuenta(cuenta.getId());
        movimientoList.forEach(mov->movimientoService.delete(mov.getId()));
        cuentaRepository.delete(cuenta);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<Cuenta> getCuentasByCliente(Long idCliente) {
        return cuentaRepository.findByClienteId(idCliente);
    }
    @Transactional
    @Override
    public ResponseEntity<Object> deleteCliente(Long idCliente) {
        Cliente cliente = clienteService.findClienteById(idCliente);
        List<Cuenta> cuentas = getCuentasByCliente(cliente.getId());
        cuentas.forEach(cuenta -> deleteCuenta(cuenta.getId()));
        clienteService.delete(cliente);
        return ResponseEntity.noContent().build();
    }


}
