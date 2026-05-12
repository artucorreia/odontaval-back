package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.SpecialismInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.SpecialismUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.SpecialismResponseDTO;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialismMapper {

  @Mappings(value = {@Mapping(target = "id", ignore = true)})
  SpecialismEntity specialismInsertRequestDTOToEntity(SpecialismInsertRequestDTO dto);

  @Mappings(value = {@Mapping(target = "id", ignore = true)})
  SpecialismEntity specialismUpdateRequestDTOToEntity(SpecialismUpdateRequestDTO dto);

  SpecialismResponseDTO specialismEntityToResponseDTO(SpecialismEntity entity);

  List<SpecialismResponseDTO> specialismEntitiesToResponseDTOs(List<SpecialismEntity> entities);
}
