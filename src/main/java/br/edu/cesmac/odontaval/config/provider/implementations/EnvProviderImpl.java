package br.edu.cesmac.odontaval.config.provider.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class EnvProviderImpl implements EnvProvider {

  @Value("${web.allowedOrigins}")
  private String allowedOrigins;

  @Value("${security.token.issuer}")
  private String tokenIssuer;

  @Value("${security.token.secret}")
  private String tokenSecret;

  @Value("${security.token.duration}")
  private Duration tokenDuration;

  @Override
  public String getAllowedOrigins() {
    return allowedOrigins;
  }

  @Override
  public String getTokenIssuer() {
    return tokenIssuer;
  }

  @Override
  public String getTokenSecret() {
    return tokenSecret;
  }

  @Override
  public Duration getTokenDuration() {
    return tokenDuration;
  }
}
