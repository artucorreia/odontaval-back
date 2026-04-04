package br.edu.cesmac.odontaval.audit;

import br.edu.cesmac.odontaval.models.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class SpringSecurityAuditAware implements AuditorAware<UUID> {
  @Override
  public Optional<UUID> getCurrentAuditor() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .filter(principal -> principal instanceof UserEntity)
        .map(principal -> (UserEntity) principal)
        .map(UserEntity::getId);
  }
}
