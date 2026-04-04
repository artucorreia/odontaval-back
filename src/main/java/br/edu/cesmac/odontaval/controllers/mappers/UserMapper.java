package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.auth.RegisterDTO;
import br.edu.cesmac.odontaval.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "roles", ignore = true),
      })
  UserEntity registerDTOToEntity(RegisterDTO registerDTO);
}
