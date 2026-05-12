package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.auth.RegisterDTO;
import br.edu.cesmac.odontaval.dtos.responses.RoleResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.UserResponseDTO;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "roles", ignore = true),
      })
  UserEntity registerDTOToEntity(RegisterDTO registerDTO);

  RoleResponseDTO roleEntityToResponseDTO(RoleEntity entity);

  UserResponseDTO userEntityToResponseDTO(UserEntity entity);

  List<UserResponseDTO> userEntitiesToResponseDTOs(List<UserEntity> entities);
}
