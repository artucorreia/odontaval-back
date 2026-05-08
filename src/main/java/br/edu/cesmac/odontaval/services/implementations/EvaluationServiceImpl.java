package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {
  private static final String ROLE_STUDENT = "STUDENT";

  private final EvaluationRepository evaluationRepository;
  private final AuthenticatedUserService authenticatedUserService;
  private final RoleService roleService;
  private final UserService userService;
  private final SpecialismService specialismService;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void insert(EvaluationEntity data) {
    log.info("Inserting a new evaluation");
    EvaluationEntity entityToSave = new EvaluationEntity();

    UserEntity professor = findAuthenticatedUser();
    entityToSave.setProfessor(professor);

    Optional<RoleEntity> optionalStudentRole =
        roleService.findByName(List.of(ROLE_STUDENT)).stream().findFirst();
    if (optionalStudentRole.isEmpty())
      throw new OdontAvalException(
          "Não foi possível encontrar a função de estudante", HttpStatus.INTERNAL_SERVER_ERROR);

    UserEntity student = userService.findById(data.getStudent().getId());
    if (student.getRoles().stream().noneMatch(role -> role.equals(optionalStudentRole.get())))
      throw new OdontAvalException(
          "Não é possível adicionar uma avaliação para usuários não cadastrados como aluno",
          HttpStatus.BAD_REQUEST);
    entityToSave.setStudent(student);

    SpecialismEntity specialism = specialismService.findById(data.getSpecialism().getId());
    entityToSave.setSpecialism(specialism);

    entityToSave.setPunctuality(data.getPunctuality());
    entityToSave.setInstrumental(data.getInstrumental());
    entityToSave.setBoxOrganization(data.getBoxOrganization());
    entityToSave.setBiosecurity(data.getBiosecurity());
    entityToSave.setEthics(data.getEthics());
    entityToSave.setConcept(data.getConcept());
    entityToSave.setGrade(data.getGrade());

    if (data.getTitle() != null && !data.getTitle().isBlank())
      entityToSave.setTitle(data.getTitle().trim());
    entityToSave.setDate(data.getDate());
    if (data.getAcademicSemester() != null)
      entityToSave.setAcademicSemester(data.getAcademicSemester());
    if (data.getGoals() != null && !data.getGoals().isBlank())
      entityToSave.setGoals(data.getGoals().trim());
    if (data.getBox() != null && !data.getBox().isBlank())
      entityToSave.setBox(data.getBox().trim());
    if (data.getProcedurePerformed() != null && !data.getProcedurePerformed().isBlank())
      entityToSave.setProcedurePerformed(data.getProcedurePerformed().trim());
    if (data.getEvaluationNumber() != null)
      entityToSave.setEvaluationNumber(data.getEvaluationNumber());
    if (data.getObservations() != null && !data.getObservations().isBlank())
      entityToSave.setObservations(data.getObservations().trim());

    entityToSave.setCreatedAt(LocalDateTime.now());
    entityToSave.setDeleted(false);

    evaluationRepository.save(entityToSave);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public List<EvaluationEntity> findAll() {
    log.info("Finding all evaluations");
    return evaluationRepository.findByDeletedFalseOrderByCreatedAtDesc();
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public List<EvaluationEntity> findByStudentId(UUID studentId) {
    log.info("Finding evaluations by studentId: {}", studentId);
    return evaluationRepository.findByStudentIdAndDeletedFalseOrderByCreatedAtDesc(studentId);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public EvaluationEntity findById(Long id) {
    log.info("Finding evaluation by id: {}", id);
    return evaluationRepository
        .findById(id)
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Nenhuma avaliação encontrada para esse id", HttpStatus.NOT_FOUND));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void update(Long id, EvaluationEntity data) {
    log.info("Updating evaluation with id: {}", id);
    EvaluationEntity existing = findById(id);

    if (data.getTitle() != null && !data.getTitle().isBlank())
      existing.setTitle(data.getTitle().trim());
    if (data.getPunctuality() != null) existing.setPunctuality(data.getPunctuality());
    if (data.getInstrumental() != null) existing.setInstrumental(data.getInstrumental());
    if (data.getBoxOrganization() != null) existing.setBoxOrganization(data.getBoxOrganization());
    if (data.getBiosecurity() != null) existing.setBiosecurity(data.getBiosecurity());
    if (data.getEthics() != null) existing.setEthics(data.getEthics());
    if (data.getConcept() != null) existing.setConcept(data.getConcept());
    if (data.getGrade() != null) existing.setGrade(data.getGrade());

    if (data.getObservations() != null && !data.getObservations().isBlank())
      existing.setObservations(data.getObservations().trim());
    if (data.getEvaluationNumber() != null)
      existing.setEvaluationNumber(data.getEvaluationNumber());
    if (data.getDate() != null) existing.setDate(data.getDate());
    if (data.getAcademicSemester() != null)
      existing.setAcademicSemester(data.getAcademicSemester());
    if (data.getGoals() != null && !data.getGoals().isBlank())
      existing.setGoals(data.getGoals().trim());
    if (data.getBox() != null && !data.getBox().isBlank()) existing.setBox(data.getBox().trim());
    if (data.getProcedurePerformed() != null && !data.getProcedurePerformed().isBlank())
      existing.setProcedurePerformed(data.getProcedurePerformed().trim());

    evaluationRepository.save(existing);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void delete(Long id) {
    log.info("Deleting evaluation with id: {}", id);
    EvaluationEntity evaluationEntity = findById(id);

    UUID userId = findAuthenticatedUserId();
    evaluationEntity.setDeletedBy(userId);
    evaluationEntity.setDeleted(true);
    evaluationEntity.setDeletedAt(LocalDateTime.now());

    evaluationRepository.save(evaluationEntity);
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
