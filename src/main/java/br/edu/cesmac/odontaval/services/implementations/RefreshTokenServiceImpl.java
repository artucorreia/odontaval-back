package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.RefreshTokenEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.RefreshTokenRepository;
import br.edu.cesmac.odontaval.services.RefreshTokenService;
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
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private static final int REFRESH_TOKEN_EXPIRY_DAYS = 7;

  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public RefreshTokenEntity create(UserEntity user) {
    log.info("Creating refresh token for userId: {}", user.getId());
    RefreshTokenEntity refreshToken =
        RefreshTokenEntity.builder()
            .user(user)
            .token(UUID.randomUUID().toString())
            .expiresAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS))
            .build();
    return refreshTokenRepository.save(refreshToken);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public RefreshTokenEntity rotate(String token) {
    log.info("Rotating refresh token");
    RefreshTokenEntity existing =
        refreshTokenRepository
            .findByToken(token)
            .orElseThrow(
                () -> new OdontAvalException("Refresh token inválido", HttpStatus.UNAUTHORIZED));

    if (existing.getExpiresAt().isBefore(LocalDateTime.now())) {
      refreshTokenRepository.delete(existing);
      throw new OdontAvalException("Refresh token expirado", HttpStatus.UNAUTHORIZED);
    }

    refreshTokenRepository.delete(existing);

    RefreshTokenEntity newToken =
        RefreshTokenEntity.builder()
            .user(existing.getUser())
            .token(UUID.randomUUID().toString())
            .expiresAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS))
            .build();
    return refreshTokenRepository.save(newToken);
  }

  @Transactional(rollbackFor = Exception.class)
  @Scheduled(cron = "0 0 * * * *")
  public void deleteExpiredTokens() {
    log.info("Running scheduled cleanup of expired refresh tokens");
    refreshTokenRepository.deleteAllExpired(LocalDateTime.now());
  }
}
