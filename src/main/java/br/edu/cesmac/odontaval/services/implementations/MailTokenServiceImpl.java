package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.MailTokenEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.models.enums.MailTokenType;
import br.edu.cesmac.odontaval.repositories.MailTokenRepository;
import br.edu.cesmac.odontaval.services.MailTokenService;
import br.edu.cesmac.odontaval.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailTokenServiceImpl implements MailTokenService {
  private static final int PASSWORD_RECOVERY_EXPIRY_MINUTES = 30;
  private static final int CONFIRM_EXPIRY_HOURS = 24;

  private final MailTokenRepository mailTokenRepository;
  private final UserService userService;

  @Override
  public String createPasswordRecoveryToken(UserEntity user) {
    MailTokenEntity mailTokenEntity =
        MailTokenEntity.builder()
            .user(user)
            .token(UUID.randomUUID().toString())
            .expiresAt(LocalDateTime.now().plusMinutes(PASSWORD_RECOVERY_EXPIRY_MINUTES))
            .type(MailTokenType.PASSWORD_RECOVERY)
            .build();
    MailTokenEntity savedMailTokenEntity = mailTokenRepository.save(mailTokenEntity);
    return savedMailTokenEntity.getToken();
  }

  @Override
  public String createConfirmToken(UserEntity user) {
    MailTokenEntity mailTokenEntity =
        MailTokenEntity.builder()
            .user(user)
            .token(UUID.randomUUID().toString())
            .expiresAt(LocalDateTime.now().plusHours(CONFIRM_EXPIRY_HOURS))
            .type(MailTokenType.CONFIRM)
            .build();
    MailTokenEntity savedMailTokenEntity = mailTokenRepository.save(mailTokenEntity);
    return savedMailTokenEntity.getToken();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void resetPassword(UUID userId, String recoveryToken, String newPassword) {
    log.info("Resetting password for userId: {}", userId);
    MailTokenEntity mailToken =
        mailTokenRepository
            .findByTokenAndUserIdAndType(recoveryToken, userId, MailTokenType.PASSWORD_RECOVERY)
            .orElseThrow(
                () -> new OdontAvalException("Token inválido", HttpStatus.BAD_REQUEST));

    if (mailToken.getExpiresAt().isBefore(LocalDateTime.now()))
      throw new OdontAvalException("Token expirado", HttpStatus.BAD_REQUEST);

    userService.resetPassword(userId, newPassword);
    mailTokenRepository.delete(mailToken);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void confirmEmail(UUID userId, String confirmToken) {
    log.info("Confirming email for userId: {}", userId);
    MailTokenEntity mailToken =
        mailTokenRepository
            .findByTokenAndUserIdAndType(confirmToken, userId, MailTokenType.CONFIRM)
            .orElseThrow(
                () -> new OdontAvalException("Token inválido", HttpStatus.BAD_REQUEST));

    if (mailToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      mailTokenRepository.delete(mailToken);
      throw new OdontAvalException("Token expirado", HttpStatus.BAD_REQUEST);
    }

    userService.verifyEmail(userId);
    mailTokenRepository.delete(mailToken);
  }

  @Transactional(rollbackFor = Exception.class)
  @Scheduled(cron = "0 0 * * * *")
  public void deleteExpiredTokens() {
    log.info("Running scheduled cleanup of expired mail tokens");
    mailTokenRepository.deleteAllExpired(LocalDateTime.now());
  }
}
