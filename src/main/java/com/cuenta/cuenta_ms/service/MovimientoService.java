package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.model.dto.MovimientoDTO;
import com.cuenta.cuenta_ms.model.dto.MovimientoSQS;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovimientoService {

    ResponseEntity<Object> getAll();

    ResponseEntity<Object> update(Long id, MovimientoDTO request);

    ResponseEntity<Object> delete(Long id);

    void receiveAndProcessAllMessages();

    List<Movimiento> getMovimientosByCuenta(Long id);

}
