package br.edu.cesmac.odontaval.services;

import java.util.UUID;

public interface MailService {
  void sendPasswordRecoveryMail(String userEmail, UUID userId, String recoveryToken);

  void sendConfirmMail(UUID userId);
}
