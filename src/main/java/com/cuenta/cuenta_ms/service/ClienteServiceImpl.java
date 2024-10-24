package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.exception.BusinessException;
import com.cuenta.cuenta_ms.exception.NotFoundException;
import com.cuenta.cuenta_ms.model.dto.ClienteDTO;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.mapper.ClienteMapper;
import com.cuenta.cuenta_ms.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    public static final String NOT_FOUND_CLIENT = "Not found client";
    @Autowired
    private ClienteRepository repository;



    @Autowired
    private ClienteMapper mapper;

    @Override
    public Cliente findClienteById(Long id) {
        return getClienteId(id);
    }

    private Cliente getClienteId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_CLIENT));
    }

    @Override
    public ResponseEntity<Object> delete(Cliente cliente) {
        repository.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
