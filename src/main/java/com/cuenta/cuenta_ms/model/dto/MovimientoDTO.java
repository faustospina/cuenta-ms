package com.cuenta.cuenta_ms.model.dto;

import java.time.LocalDate;

public record MovimientoDTO(Long id,LocalDate fecha,TipoMovimiento tipoMovimiento,double valor,double saldo,Long cuentaId) {
}
