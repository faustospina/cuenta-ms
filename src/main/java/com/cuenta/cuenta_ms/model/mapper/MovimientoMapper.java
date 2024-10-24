package com.cuenta.cuenta_ms.model.mapper;

import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.model.dto.MovimientoDTO;
import com.cuenta.cuenta_ms.model.dto.TipoCuenta;
import com.cuenta.cuenta_ms.model.dto.TipoMovimiento;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovimientoMapper {
    @Mapping(target = "tipoMovimiento", source = "tipoMovimiento", qualifiedByName = "stringToEnum")
    MovimientoDTO toDTO(Movimiento entity);

    @Mapping(target = "tipoMovimiento", source = "tipoMovimiento", qualifiedByName = "enumToString")
    Movimiento toEntity(MovimientoDTO dto);

    @Named("enumToString")
    default String enumToString(TipoMovimiento tipoCuenta) {
        return tipoCuenta.name();
    }

    @Named("stringToEnum")
    default TipoMovimiento stringToEnum(String tipoCuenta) {
        return TipoMovimiento.valueOf(tipoCuenta);
    }
}
