package br.edu.cesmac.odontaval.config.provider.implementations;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvProviderImpl implements EnvProvider {

  @Value("${web.allowedOrigins}")
  private String allowedOrigins;

  @Override
  public String getAllowedOrigins() {
    return allowedOrigins;
  }
}
