package com.cuenta.cuenta_ms.repository;

import com.cuenta.cuenta_ms.model.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate inicio, LocalDate fin);
    List<Movimiento> findByCuentaId(Long cuentaId);
}
