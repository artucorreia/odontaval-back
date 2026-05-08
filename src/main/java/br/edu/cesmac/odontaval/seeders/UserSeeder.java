package br.edu.cesmac.odontaval.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.RoleRepository;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSeeder implements Seeder {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public void run() {
    if (userRepository.count() != 0) {
      log.info("There are already users. Skipping seeder.");
      return;
    }
    log.info("Inserting users...");
    Collection<UserEntity> usersToInsert = getEntities();
    userRepository.saveAll(usersToInsert);
    log.info("Users inserted successfully.");
  }

  private Collection<UserEntity> getEntities() {
    Set<RoleEntity> roles = roleRepository.findByNameInIgnoreCase(List.of("ADMIN", "PROFESSOR", "STUDENT"));

    UserEntity firstUser = new UserEntity();
    firstUser.setName("Arthur Correia");
    firstUser.setEmail("arthurcorreia.dev@gmail.com");
    firstUser.setRoles(roles);
    firstUser.setPassword(encoder.encode("12345678"));
    firstUser.setCreatedAt(LocalDateTime.now());
    firstUser.setDeleted(false);

    UserEntity secondUser = new UserEntity();
    secondUser.setName("Hugo Steverson");
    secondUser.setEmail("hugosteverson11@gmail.com");
    secondUser.setRoles(roles);
    secondUser.setPassword(encoder.encode("12345678"));
    secondUser.setCreatedAt(LocalDateTime.now());
    secondUser.setDeleted(false);


    return new ArrayList<>(List.of(firstUser, secondUser));
  }
}
