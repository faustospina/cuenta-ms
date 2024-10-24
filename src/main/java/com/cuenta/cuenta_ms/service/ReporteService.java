package com.cuenta.cuenta_ms.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface ReporteService {

    ResponseEntity<Object> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
