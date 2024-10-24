package com.cuenta.cuenta_ms.repository;

import com.cuenta.cuenta_ms.model.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {

}
