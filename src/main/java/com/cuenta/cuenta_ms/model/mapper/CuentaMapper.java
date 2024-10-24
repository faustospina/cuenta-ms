package com.cuenta.cuenta_ms.model.mapper;

import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.model.dto.TipoCuenta;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CuentaMapper {
    @Mapping(target = "tipoCuenta", source = "tipoCuenta", qualifiedByName = "stringToEnum")
    CuentaDTO toDTO(Cuenta entity);

    @Mapping(target = "tipoCuenta", source = "tipoCuenta", qualifiedByName = "enumToString")
    Cuenta toEntity(CuentaDTO dto);

    @Named("enumToString")
    default String enumToString(TipoCuenta tipoCuenta) {
        return tipoCuenta.name();
    }

    @Named("stringToEnum")
    default TipoCuenta stringToEnum(String tipoCuenta) {
        return TipoCuenta.valueOf(tipoCuenta);
    }
}
