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

  List<UserEntity> findAll();

  List<UserEntity> findByRole(String role);

  void insert(UserEntity userEntity);

  void insertWithRole(UserEntity userEntity, String roleName);

  UserEntity update(UUID id, UserUpdateRequestDTO dto);

  void updatePassword(UUID id, PasswordUpdateRequestDTO dto);

  void resetPassword(UUID id, String newPassword);

  void verifyEmail(UUID id);

  void updateRole(UUID id, String roleName);

  void delete(UUID id);

  void reactivate(UUID id);

  String extractRoleName(Set<RoleEntity> roleEntities);
}
