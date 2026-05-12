package br.edu.cesmac.odontaval.config.provider.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class EnvProviderImpl implements EnvProvider {

  @Value("${spring.mail.username}")
  private String mailUsername;

  @Value("${web.address}")
  private String webAddress;

  @Value("${web.allowedOrigins}")
  private String allowedOrigins;

  @Value("${security.api.key}")
  private String apiKey;

  @Value("${security.token.issuer}")
  private String tokenIssuer;

  @Value("${security.token.secret}")
  private String tokenSecret;

  @Value("${security.token.duration}")
  private Duration tokenDuration;

  @Override
  public String getMailUsername() {
    return mailUsername;
  }

  @Override
  public String getWebAddress() {
    return webAddress;
  }

  @Override
  public String getAllowedOrigins() {
    return allowedOrigins;
  }

  @Override
  public String getApiKey() {
    return apiKey;
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
