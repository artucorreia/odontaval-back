package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.services.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailSenderServiceImpl implements MailSenderService {
  private final JavaMailSender javaMailSender;

  @Override
  public void send(String userEmail, String url, String template)
      throws MailException, MessagingException {
    log.info("Sending a mail to user");

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo("<" + userEmail + ">");
    helper.setFrom("noreply@grafmarques.com.br");
    helper.setSubject("Recuperação de senha");

    helper.setText("Acesse este link para alterar sua senha: " + url, template);

    javaMailSender.send(message);
  }
}
