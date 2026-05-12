package br.edu.cesmac.odontaval.security;

import br.edu.cesmac.odontaval.config.provider.EnvProvider;
import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationApiKeyService {
  private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

  private final EnvProvider envProvider;

  public Authentication getAuthentication(HttpServletRequest request) {
    String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
    if (requestApiKey == null || !requestApiKey.equals(envProvider.getApiKey())) {
      throw new OdontAvalException("Invalid API KEY", HttpStatus.UNAUTHORIZED);
    }

    return new ApiKeyAuthentication(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
  }
}
