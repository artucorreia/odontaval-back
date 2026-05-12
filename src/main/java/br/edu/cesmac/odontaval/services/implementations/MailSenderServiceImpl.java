package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import br.edu.cesmac.odontaval.services.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailSenderServiceImpl implements MailSenderService {
  private final EnvProvider envProvider;
  private final JavaMailSender javaMailSender;

  @Override
  public void send(String subject, String plainText, String userEmail, String url, String template)
      throws MailException, MessagingException {
    log.info("Sending a mail to user: {}", subject);

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(userEmail);
    helper.setFrom(envProvider.getMailUsername());
    helper.setSubject(subject);

    helper.setText(plainText, template);

    javaMailSender.send(message);
  }
}
