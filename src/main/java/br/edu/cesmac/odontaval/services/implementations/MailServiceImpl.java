package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.services.MailSenderService;
import br.edu.cesmac.odontaval.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailServiceImpl implements MailService {
  private static final String PASSWORD_RECOVERY_MAIL_TEMPLATE_PATH =
      "templates/password-recovery-mail.html";
  private static final String CONFIRM_MAIL_TEMPLATE_PATH = "templates/confirm-mail.html";

  private static final String PASSWORD_RECOVERY_PATH = "/reset-password";
  private static final String CONFIRM_MAIL_PATH = "/confirm-email";

  private final EnvProvider envProvider;
  private final MailSenderService mailSenderService;

  @Override
  public void sendPasswordRecoveryMail(String userEmail, UUID userId, String recoveryToken) {
    log.info("Preparing password recovery message");
    try {
      String template = getMailTemplate(PASSWORD_RECOVERY_MAIL_TEMPLATE_PATH);
      String currentYear = String.valueOf(LocalDate.now().getYear());
      String url =
          envProvider.getWebAddress()
              + PASSWORD_RECOVERY_PATH
              + String.format("?userId=%s&token=%s", userId, recoveryToken);

      template =
          template
              .replace("${userEmail}", userEmail)
              .replace("${url}", url)
              .replace("${currentYear}", currentYear);
      mailSenderService.send(
          "Recuperação de senha - ODONTAVAL",
          "Acesse este link para alterar sua senha: " + url,
          userEmail,
          url,
          template);
    } catch (Exception e) {
      throw new OdontAvalException(
          "Ocorreu um erro ao tentar enviar e-mail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void sendConfirmMail(String userEmail, UUID userId, String userName, String confirmToken) {
    log.info("Preparing confirm mail message");
    try {
      String template = getMailTemplate(CONFIRM_MAIL_TEMPLATE_PATH);
      String currentYear = String.valueOf(LocalDate.now().getYear());
      String url =
          envProvider.getWebAddress()
              + CONFIRM_MAIL_PATH
              + String.format("?userId=%s&token=%s", userId, confirmToken);

      template =
          template
              .replace("${userName}", userName)
              .replace("${url}", url)
              .replace("${currentYear}", currentYear);
      mailSenderService.send(
          "Confirmação de e-mail - ODONTAVAL",
          "Acesse este link para confirmar seu e-mail: " + url,
          userEmail,
          url,
          template);
    } catch (Exception e) {
      log.error(e.getCause().toString());
      throw new OdontAvalException(
          "Ocorreu um erro ao tentar enviar e-mail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private String getMailTemplate(String path) throws IOException {
    ClassPathResource resource = new ClassPathResource(path);
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }
}
