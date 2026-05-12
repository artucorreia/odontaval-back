package br.edu.cesmac.odontaval.security;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
  private final AuthorizationApiKeyService authorizationApiKeyService;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  public void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    log.info("Checking api key");
    try {
      Authentication authentication = authorizationApiKeyService.getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (OdontAvalException e) {
      resolver.resolveException(request, response, null, e);
      return;
    } catch (Exception e) {
      resolver.resolveException(
          request, response, null,
          new OdontAvalException("Api Key incorreta ou inválida", HttpStatus.UNAUTHORIZED));
      return;
    }

    filterChain.doFilter(request, response);
  }
}
