package br.edu.cesmac.odontaval.config.provider;

import java.time.Duration;

public interface EnvProvider {
  String getMailUsername();

  String getWebAddress();

  String getAllowedOrigins();

  String getApiKey();

  String getTokenIssuer();

  String getTokenSecret();

  Duration getTokenDuration();
}
