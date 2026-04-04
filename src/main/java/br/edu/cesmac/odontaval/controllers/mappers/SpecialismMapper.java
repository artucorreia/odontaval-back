package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.auth.RegisterDTO;
import br.edu.cesmac.odontaval.dtos.requests.SpecialismRequestDTO;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SpecialismMapper {
  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
      })
  SpecialismEntity specialismRequestDTOToEntity(SpecialismRequestDTO specialismRequestDTO);
}
