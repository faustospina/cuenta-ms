package com.cuenta.cuenta_ms.model.mapper;

import com.cuenta.cuenta_ms.model.dto.ClienteDTO;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente entity);
    Cliente toEntity(ClienteDTO dto);
}
