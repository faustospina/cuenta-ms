package com.cuenta.cuenta_ms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaReporte {
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private List<MovimientoReporte> movimientos;
}
