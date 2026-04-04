package br.edu.cesmac.odontaval.services.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import br.edu.cesmac.odontaval.services.RoleService;
import br.edu.cesmac.odontaval.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder encoder = new BCryptPasswordEncoder();


  @Override
  public UserEntity findById(UUID id) {
    log.info("Finding user by id: {}", id);
    return userRepository
        .findById(id)
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Nenhum usuário encontrado para esse id", HttpStatus.NOT_FOUND));
  }

  @Override
  public UserEntity findByEmail(String email) {
    log.info("Finding user by email: {}", email);
    return userRepository
        .findByEmailIgnoreCase(email.trim())
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Nenhum usuário encontrado para esse e-mail", HttpStatus.NOT_FOUND));
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insert(UserEntity newUser) {
    log.info("Inserting a new user");
    Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(newUser.getEmail().trim());
    if (optionalUserEntity.isPresent())
      throw new OdontAvalException("O e-mail informado já está em uso", HttpStatus.BAD_REQUEST);

    newUser.setName(newUser.getName().trim());
    newUser.setEmail(newUser.getEmail().trim());
    newUser.setCreatedAt(LocalDateTime.now());
    newUser.setDeleted(false);
    String password = encoder.encode(newUser.getPassword());
    Set<RoleEntity> roles = roleService.findByName(List.of("STUDENT"));
    newUser.setPassword(password);
    newUser.setRoles(roles);
    userRepository.save(newUser);
  }
}
