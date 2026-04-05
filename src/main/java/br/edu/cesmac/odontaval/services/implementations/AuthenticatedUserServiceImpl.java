package br.edu.cesmac.odontaval.services.implementations;

import java.util.Optional;
import java.util.UUID;

import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.services.AuthenticatedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
  @Override
  public Optional<UserEntity> findCurrentUser() {
    log.info("Finding current authenticated user");
    return Optional.of(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(UserEntity.class::cast);
  }

  @Override
  public Optional<UUID> findCurrentUserId() {
    log.info("Finding current authenticated user id");
    Optional<UserEntity> optionalUserEntity =
        Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(UserEntity.class::cast);
    return optionalUserEntity.map(UserEntity::getId);
  }
}
