package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthenticatedUserService {
  Optional<UserEntity> findCurrentUser();

  Optional<UUID> findCurrentUserId();
}
