package br.edu.cesmac.odontaval.services;

import java.util.UUID;

public interface MailService {
  void sendPasswordRecoveryMail(String userEmail, UUID userId, String recoveryToken);

  void sendConfirmMail(String userEmail, UUID userId, String userName, String confirmToken);
}
