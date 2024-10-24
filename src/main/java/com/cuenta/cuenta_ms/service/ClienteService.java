package com.cuenta.cuenta_ms.service;


import com.cuenta.cuenta_ms.model.dto.ClienteDTO;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import org.springframework.http.ResponseEntity;

public interface ClienteService {
    Cliente findClienteById(Long id);

    ResponseEntity<Object> delete(Cliente cliente);

}
