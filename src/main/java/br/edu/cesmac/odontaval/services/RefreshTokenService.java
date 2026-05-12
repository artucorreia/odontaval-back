package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.RefreshTokenEntity;
import br.edu.cesmac.odontaval.models.UserEntity;

public interface RefreshTokenService {
  RefreshTokenEntity create(UserEntity user);

  RefreshTokenEntity rotate(String token);
}
