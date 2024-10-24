package com.cuenta.cuenta_ms.model.mapper;

import com.cuenta.cuenta_ms.model.dto.PersonaDTO;
import com.cuenta.cuenta_ms.model.entities.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonaMapper {

    PersonaDTO toDTO(Persona entity);
    Persona toEntity(PersonaDTO dto);

}
