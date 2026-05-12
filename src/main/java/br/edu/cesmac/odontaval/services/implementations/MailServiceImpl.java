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
  private final EnvProvider envProvider;
  private final MailSenderService mailSenderService;

  @Override
  public void sendPasswordRecoveryMail(String userEmail, UUID userId, String recoveryToken) {
    log.info("Preparing password recovery message");
    try {
      String template = getMailTemplate("templates/password-recovery-mail.html");
      String currentYear = String.valueOf(LocalDate.now().getYear());
      String url =
          envProvider.getWebAddress() + String.format("?userId=%s&token=%s", userId, recoveryToken);

      template =
          template
              .replace("${userEmail}", userEmail)
              .replace("${url}", url)
              .replace("${currentYear}", currentYear);
      mailSenderService.send(userEmail, url, template);
    } catch (Exception e) {
      throw new OdontAvalException(
          "Ocorreu um erro ao tentar enviar e-mail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void sendConfirmMail(UUID userId) {}

  private String getMailTemplate(String path) throws IOException {
    ClassPathResource resource = new ClassPathResource(path);
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }
}
