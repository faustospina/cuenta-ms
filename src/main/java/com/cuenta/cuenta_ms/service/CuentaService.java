package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CuentaService {

    ResponseEntity<Object> createCount(Long idCliente, CuentaDTO cuenta);
    ResponseEntity<Object> getAll();

    ResponseEntity<Object> updateCuenta(Long idCuenta, CuentaDTO cuenta);

    ResponseEntity<Object> deleteCuenta(Long idCuenta);

    List<Cuenta> getCuentasByCliente(Long idCliente);

    ResponseEntity<Object> deleteCliente(Long idCliente);

}
