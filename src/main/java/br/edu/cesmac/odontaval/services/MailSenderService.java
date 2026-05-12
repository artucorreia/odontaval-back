package br.edu.cesmac.odontaval.services;

import jakarta.mail.MessagingException;

import java.util.UUID;

public interface MailSenderService {
  void send(String subject, String plainText, String userEmail, String url, String template)
      throws MessagingException;
}
