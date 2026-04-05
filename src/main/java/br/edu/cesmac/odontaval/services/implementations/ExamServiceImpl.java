package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.ExamRepository;
import br.edu.cesmac.odontaval.services.AuthenticatedUserService;
import br.edu.cesmac.odontaval.services.ExamService;
import br.edu.cesmac.odontaval.services.SpecialismService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
  private final ExamRepository examRepository;
  private final SpecialismService specialismService;
  private final AuthenticatedUserService authenticatedUserService;

  @Override
  public List<ExamEntity> findAll() {
    log.info("Finding all exams");
    return examRepository.findAll();
  }

  @Override
  public ExamEntity findById(Long id) {
    log.info("Finding exam by id: {}", id);
    return examRepository
        .findById(id)
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Nenhum exame encontrado para esse id", HttpStatus.NOT_FOUND));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void insert(ExamEntity examEntity) {
    log.info("Inserting a new exam");
    ExamEntity examEntityToSave = new ExamEntity();

    examEntityToSave.setTitle(examEntity.getTitle().trim());
    examEntityToSave.setDate(examEntity.getDate());
    examEntityToSave.setAcademicSemester(examEntity.getAcademicSemester());
    examEntityToSave.setGoals(examEntity.getGoals().trim());
    examEntityToSave.setProcedurePerformed(examEntity.getProcedurePerformed().trim());
    examEntityToSave.setServiceUnit(examEntity.getServiceUnit().trim());
    UserEntity userEntity = findAuthenticatedUser();
    examEntityToSave.setProfessor(userEntity);
    SpecialismEntity specialismEntity =
        specialismService.findById(examEntity.getSpecialism().getId());
    examEntityToSave.setSpecialism(specialismEntity);
    examEntityToSave.setCreatedAt(LocalDateTime.now());
    examEntityToSave.setDeleted(false);

    examRepository.save(examEntityToSave);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void update(Long id, ExamEntity examEntity) {
    log.info("Updating a exam with id: {}", id);
    ExamEntity existingExam = this.findById(id);

    if (examEntity.getTitle() != null) existingExam.setTitle(examEntity.getTitle().trim());
    if (examEntity.getDate() != null) existingExam.setDate(examEntity.getDate());
    if (examEntity.getAcademicSemester() != null)
      existingExam.setAcademicSemester(examEntity.getAcademicSemester());
    if (examEntity.getGoals() != null) existingExam.setGoals(examEntity.getGoals());
    if (examEntity.getProcedurePerformed() != null)
      existingExam.setProcedurePerformed(examEntity.getProcedurePerformed());
    if (examEntity.getServiceUnit() != null)
      existingExam.setServiceUnit(examEntity.getServiceUnit());
    if (examEntity.getSpecialism().getId() != null) {
      SpecialismEntity specialismEntity =
          specialismService.findById(examEntity.getSpecialism().getId());
      existingExam.setSpecialism(specialismEntity);
    }
    examRepository.save(existingExam);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void delete(Long id) {
    log.info("Deleting a exam by id: {}", id);
    ExamEntity examEntity = this.findById(id);

    UUID userId = findAuthenticatedUserId();
    examEntity.setDeletedBy(userId);
    examEntity.setDeleted(true);
    examEntity.setDeletedAt(examEntity.getCreatedAt());

    examRepository.save(examEntity);
  }

  private UserEntity findAuthenticatedUser() {
    return authenticatedUserService
        .findCurrentUser()
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Ocorreu um erro ao buscar o usuário autenticado",
                    HttpStatus.INTERNAL_SERVER_ERROR));
  }

  private UUID findAuthenticatedUserId() {
    return authenticatedUserService
        .findCurrentUserId()
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Ocorreu um erro ao buscar o usuário autenticado",
                    HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
