package br.edu.cesmac.odontaval.config.provider;

import java.time.Duration;

public interface EnvProvider {
  String getAllowedOrigins();

  String getTokenIssuer();

  String getTokenSecret();

  Duration getTokenDuration();
}
