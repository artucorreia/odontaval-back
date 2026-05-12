package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByToken(String token);

  @Modifying
  @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now")
  void deleteAllExpired(LocalDateTime now);
}
