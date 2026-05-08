package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.dtos.requests.PasswordUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.UserUpdateRequestDTO;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
  UserEntity findById(UUID id);

  UserEntity findByEmail(String email);

  List<UserEntity> findByRole(String role);

  void insert(UserEntity userEntity);

  UserEntity update(UUID id, UserUpdateRequestDTO dto);

  void updatePassword(UUID id, PasswordUpdateRequestDTO dto);

  String extractRoleName(Set<RoleEntity> roleEntities);
}
