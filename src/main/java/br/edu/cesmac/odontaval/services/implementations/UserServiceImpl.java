package br.edu.cesmac.odontaval.services.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.cesmac.odontaval.dtos.requests.PasswordUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.UserUpdateRequestDTO;
import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import br.edu.cesmac.odontaval.services.AuthenticatedUserService;
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
  private final AuthenticatedUserService authenticatedUserService;
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

  @Override
  public List<UserEntity> findAll() {
    log.info("Finding all users including deleted");
    return userRepository.findAllByOrderByCreatedAtDesc();
  }

  @Override
  public List<UserEntity> findByRole(String roleName) {
    log.info("Finding users by exclusive role: {}", roleName);
    return userRepository.findByExclusiveRoleIgnoreCase(roleName);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insert(UserEntity newUser) {
    log.info("Inserting a new user");
    Optional<UserEntity> optionalUserEntity =
        userRepository.findByEmailIgnoreCase(newUser.getEmail().trim());
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

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insertWithRole(UserEntity newUser, String roleName) {
    log.info("Inserting a new user with role: {}", roleName);
    Optional<UserEntity> optionalUserEntity =
        userRepository.findByEmailIgnoreCase(newUser.getEmail().trim());
    if (optionalUserEntity.isPresent())
      throw new OdontAvalException("O e-mail informado já está em uso", HttpStatus.BAD_REQUEST);

    newUser.setName(newUser.getName().trim());
    newUser.setEmail(newUser.getEmail().trim());
    newUser.setCreatedAt(LocalDateTime.now());
    newUser.setDeleted(false);
    newUser.setPassword(encoder.encode(newUser.getPassword()));

    Set<RoleEntity> roles = roleService.findByName(List.of(roleName.toUpperCase()));
    if (roles.isEmpty())
      throw new OdontAvalException("Role inválida: " + roleName, HttpStatus.BAD_REQUEST);
    newUser.setRoles(roles);

    userRepository.save(newUser);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public UserEntity update(UUID id, UserUpdateRequestDTO dto) {
    log.info("Updating user: {}", id);
    UserEntity user = findById(id);

    if (dto.getName() != null && !dto.getName().isBlank()) {
      user.setName(dto.getName().trim());
    }
    if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
      String newEmail = dto.getEmail().trim();
      if (!newEmail.equalsIgnoreCase(user.getEmail())) {
        userRepository.findByEmailIgnoreCase(newEmail).ifPresent(existing -> {
          throw new OdontAvalException("O e-mail informado já está em uso", HttpStatus.BAD_REQUEST);
        });
        user.setEmail(newEmail);
      }
    }

    return userRepository.save(user);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updatePassword(UUID id, PasswordUpdateRequestDTO dto) {
    log.info("Updating password for user: {}", id);
    UserEntity user = findById(id);

    if (!encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
      throw new OdontAvalException("Senha atual incorreta", HttpStatus.BAD_REQUEST);
    }

    user.setPassword(encoder.encode(dto.getNewPassword()));
    userRepository.save(user);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void adminResetPassword(UUID id, String newPassword) {
    log.info("Admin resetting password for user: {}", id);
    UserEntity user = findById(id);
    user.setPassword(encoder.encode(newPassword));
    userRepository.save(user);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateRole(UUID id, String roleName) {
    log.info("Updating role for user: {} to {}", id, roleName);
    UserEntity user = findById(id);

    Set<RoleEntity> roles = roleService.findByName(List.of(roleName.toUpperCase()));
    if (roles.isEmpty())
      throw new OdontAvalException("Role inválida: " + roleName, HttpStatus.BAD_REQUEST);
    user.setRoles(roles);

    userRepository.save(user);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(UUID id) {
    log.info("Deactivating user: {}", id);
    UserEntity user = findById(id);

    UUID adminId = authenticatedUserService.findCurrentUserId()
        .orElseThrow(() -> new OdontAvalException(
            "Ocorreu um erro ao buscar o usuário autenticado", HttpStatus.INTERNAL_SERVER_ERROR));

    user.setDeleted(true);
    user.setDeletedAt(LocalDateTime.now());
    user.setDeletedBy(adminId);

    userRepository.save(user);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void reactivate(UUID id) {
    log.info("Reactivating user: {}", id);
    UserEntity user = findById(id);
    user.setDeleted(false);
    user.setDeletedAt(null);
    user.setDeletedBy(null);
    userRepository.save(user);
  }

  @Override
  public String extractRoleName(Set<RoleEntity> roleEntities) {
    List<String> priority = List.of("ADMIN", "PROFESSOR", "STUDENT");
    Set<String> userRoleNames =
        roleEntities.stream().map(RoleEntity::getName).collect(Collectors.toSet());
    return priority.stream().filter(userRoleNames::contains).findFirst().orElse("");
  }
}
