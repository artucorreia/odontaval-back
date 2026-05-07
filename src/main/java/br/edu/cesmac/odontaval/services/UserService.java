package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;

import java.util.Set;
import java.util.UUID;

public interface UserService {
  UserEntity findById(UUID id);

  UserEntity findByEmail(String email);

  void insert(UserEntity userEntity);

  String extractRoleName(Set<RoleEntity> roleEntities);
}
