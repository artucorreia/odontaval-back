package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.MailTokenEntity;
import br.edu.cesmac.odontaval.models.UserEntity;

import java.util.UUID;

public interface MailTokenService {
  String createPasswordRecoveryToken(UserEntity user);

  String createConfirmToken(UserEntity user);

  void resetPassword(UUID userId, String recoveryToken, String newPassword);
}
