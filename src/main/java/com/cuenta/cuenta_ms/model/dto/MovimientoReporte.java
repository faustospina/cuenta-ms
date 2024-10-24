package com.cuenta.cuenta_ms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoReporte {
    private LocalDate fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldoDisponible;
    private String estado;
    private String descripcion;
}
