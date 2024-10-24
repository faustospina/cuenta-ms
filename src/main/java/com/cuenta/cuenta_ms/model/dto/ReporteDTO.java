package com.cuenta.cuenta_ms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {

    private String cliente;
    private List<CuentaReporte> cuentas;

}
