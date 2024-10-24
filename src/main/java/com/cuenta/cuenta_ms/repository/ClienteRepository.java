package com.cuenta.cuenta_ms.repository;

import com.cuenta.cuenta_ms.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
