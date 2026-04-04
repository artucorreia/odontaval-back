package br.edu.cesmac.odontaval.security.services.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import br.edu.cesmac.odontaval.security.services.GenerateToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateTokenAuth0Impl implements GenerateToken {

  private final EnvProvider envProvider;

  @Override
  public String generate(UUID userId) {
    log.info("Generating token jwt for user: {}", userId);
    Algorithm algorithm = Algorithm.HMAC256(envProvider.getTokenSecret());
    Instant issuedAt = Instant.now();
    Instant expiresAt = issuedAt.plus(envProvider.getTokenDuration());
    return JWT.create()
        .withIssuer(envProvider.getTokenIssuer())
        .withSubject(userId.toString())
        .withIssuedAt(issuedAt)
        .withExpiresAt(expiresAt)
        .sign(algorithm);
  }
}
