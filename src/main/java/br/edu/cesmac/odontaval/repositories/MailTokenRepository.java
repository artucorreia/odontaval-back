package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.MailTokenEntity;
import br.edu.cesmac.odontaval.models.enums.MailTokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MailTokenRepository extends JpaRepository<MailTokenEntity, Long> {
  Optional<MailTokenEntity> findByTokenAndUserIdAndType(String token, UUID userId, MailTokenType type);

  @Modifying
  @Query("DELETE FROM MailTokenEntity m WHERE m.expiresAt < :now")
  void deleteAllExpired(LocalDateTime now);
}
