package br.edu.cesmac.odontaval.security.services.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import br.edu.cesmac.odontaval.security.services.ValidateToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateAuth0TokenImpl implements ValidateToken {

  private final EnvProvider envProvider;

  @Override
  public UUID validate(String token) {
    log.info("Validating token: {}", token);
    Algorithm algorithm = Algorithm.HMAC256(envProvider.getTokenSecret());
    String subject =
        JWT.require(algorithm)
            .withIssuer(envProvider.getTokenIssuer())
            .build()
            .verify(token)
            .getSubject();
    return UUID.fromString(subject);
  }
}
