package br.edu.cesmac.odontaval.security;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.security.services.ValidateToken;
import br.edu.cesmac.odontaval.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final ValidateToken validateToken;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = recoverToken(request);
      if (token != null) {
        UUID userId = validateToken.validate(token);
        if (userId == null)
          throw new OdontAvalException("Token inválido ou expirado", HttpStatus.FORBIDDEN);

        UserEntity user = userService.findById(userId);
        if (user.getDeleted()) throw new OdontAvalException("Usuário inativo", HttpStatus.FORBIDDEN);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                customUserDetails.getUser(), null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (OdontAvalException ex) {
      resolver.resolveException(request, response, null, ex);
      return;
    } catch (Exception ex) {
      resolver.resolveException(
          request, response, null,
          new OdontAvalException("Erro na validação do token", HttpStatus.FORBIDDEN));
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
