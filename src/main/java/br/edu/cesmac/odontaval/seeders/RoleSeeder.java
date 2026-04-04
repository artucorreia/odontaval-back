package br.edu.cesmac.odontaval.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleSeeder implements Seeder {

  private final RoleRepository roleRepository;

  @Override
  public void run() {
    if (roleRepository.count() != 0) {
      log.info("There are already roles. Skipping seeder.");
      return;
    }
    log.info("Inserting roles...");
    Collection<RoleEntity> rolesToInsert = getEntities();
    roleRepository.saveAll(rolesToInsert);
    log.info("Roles inserted successfully.");
  }

  private Collection<RoleEntity> getEntities() {
    RoleEntity adminRole = new RoleEntity();
    adminRole.setName("ADMIN");
    adminRole.setCreatedAt(LocalDateTime.now());
    adminRole.setDeleted(false);

    RoleEntity professorRole = new RoleEntity();
    professorRole.setName("PROFESSOR");
    professorRole.setCreatedAt(LocalDateTime.now());
    professorRole.setDeleted(false);

    RoleEntity studentRole = new RoleEntity();
    studentRole.setName("STUDENT");
    studentRole.setCreatedAt(LocalDateTime.now());
    studentRole.setDeleted(false);

    return new ArrayList<>(List.of(adminRole, professorRole, studentRole));
  }
}
