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
    Set<RoleEntity> studentRoles =
        roleRepository.findByNameInIgnoreCase(List.of("STUDENT"));

    Set<RoleEntity> professorRoles =
        roleRepository.findByNameInIgnoreCase(List.of("PROFESSOR"));

    Set<RoleEntity> adminRoles =
        roleRepository.findByNameInIgnoreCase(List.of("ADMIN"));

    // ====================== STUDENTS ======================

    UserEntity firstStudent = new UserEntity();
    firstStudent.setName("Arthur Correia");
    firstStudent.setEmail("arthurcorreia.dev@gmail.com");
    firstStudent.setRoles(adminRoles);
    firstStudent.setPassword(encoder.encode("12345678"));
    firstStudent.setCreatedAt(LocalDateTime.now());
    firstStudent.setDeleted(false);

    UserEntity secondStudent = new UserEntity();
    secondStudent.setName("Hugo Steverson");
    secondStudent.setEmail("hugosteverson11@gmail.com");
    secondStudent.setRoles(studentRoles);
    secondStudent.setPassword(encoder.encode("12345678"));
    secondStudent.setCreatedAt(LocalDateTime.now());
    secondStudent.setDeleted(false);

    UserEntity thirdStudent = new UserEntity();
    thirdStudent.setName("Tirth Patel");
    thirdStudent.setEmail("tirthpatel@gmail.com");
    thirdStudent.setRoles(studentRoles);
    thirdStudent.setPassword(encoder.encode("12345678"));
    thirdStudent.setCreatedAt(LocalDateTime.now());
    thirdStudent.setDeleted(false);

    UserEntity fourthStudent = new UserEntity();
    fourthStudent.setName("John Doe");
    fourthStudent.setEmail("johndoe@gmail.com");
    fourthStudent.setRoles(studentRoles);
    fourthStudent.setPassword(encoder.encode("12345678"));
    fourthStudent.setCreatedAt(LocalDateTime.now());
    fourthStudent.setDeleted(false);

    // ====================== PROFESSORS ======================

    UserEntity firstProfessor = new UserEntity();
    firstProfessor.setName("Dr. Carlos Henrique");
    firstProfessor.setEmail("carlos.henrique@gmail.com");
    firstProfessor.setRoles(professorRoles);
    firstProfessor.setPassword(encoder.encode("12345678"));
    firstProfessor.setCreatedAt(LocalDateTime.now());
    firstProfessor.setDeleted(false);

    UserEntity secondProfessor = new UserEntity();
    secondProfessor.setName("Dra. Fernanda Lima");
    secondProfessor.setEmail("fernanda.lima@gmail.com");
    secondProfessor.setRoles(professorRoles);
    secondProfessor.setPassword(encoder.encode("12345678"));
    secondProfessor.setCreatedAt(LocalDateTime.now());
    secondProfessor.setDeleted(false);

    return new ArrayList<>(List.of(
        firstStudent,
        secondStudent,
        thirdStudent,
        fourthStudent,
        firstProfessor,
        secondProfessor
    ));
  }
}
