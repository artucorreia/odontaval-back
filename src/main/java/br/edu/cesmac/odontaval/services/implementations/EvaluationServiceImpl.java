package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {
  private final String ROLE_STUDENT = "STUDENT";
  private final EvaluationRepository evaluationRepository;
  private final AuthenticatedUserService authenticatedUserService;
  private final RoleService roleService;
  private final ExamService examService;
  private final UserService userService;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void insert(EvaluationEntity data) {
    log.info("Inserting a new evaluation");
    EvaluationEntity evaluationEntityToSave = new EvaluationEntity();

    ExamEntity examEntity = examService.findById(data.getExam().getId());
    if (examEntity.getDate().isBefore(LocalDate.now()))
      throw new OdontAvalException(
          "Não é possível adicionar uma avalição antes da data do exame", HttpStatus.BAD_REQUEST);
    evaluationEntityToSave.setExam(examEntity);

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
    evaluationEntityToSave.setStudent(student);

    evaluationEntityToSave.setPunctuality(data.getPunctuality());
    evaluationEntityToSave.setInstrumental(data.getInstrumental());
    evaluationEntityToSave.setOrganizationOfServiceUnit(data.getOrganizationOfServiceUnit());
    evaluationEntityToSave.setBiosecurity(data.getBiosecurity());
    evaluationEntityToSave.setEthics(data.getEthics());
    evaluationEntityToSave.setConcept(data.getConcept());

    if (data.getObservations() != null && !data.getObservations().isEmpty()) {
      evaluationEntityToSave.setObservations(data.getObservations().trim());
    }

    evaluationEntityToSave.setCreatedAt(LocalDateTime.now());
    evaluationEntityToSave.setDeleted(false);

    this.evaluationRepository.save(evaluationEntityToSave);
  }

  @Override
  public List<EvaluationEntity> findAll() {
    log.info("Finding all evaluations");
    return this.evaluationRepository.findAll();
  }

  @Override
  public EvaluationEntity findById(Long id) {
    log.info("Finding evaluation by id: {}", id);
    return this.evaluationRepository
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
    EvaluationEntity existingEvaluation = this.findById(id);

    if (data.getPunctuality() != null) existingEvaluation.setPunctuality(data.getPunctuality());
    if (data.getInstrumental() != null) existingEvaluation.setInstrumental(data.getInstrumental());
    if (data.getOrganizationOfServiceUnit() != null)
      existingEvaluation.setOrganizationOfServiceUnit(data.getOrganizationOfServiceUnit());
    if (data.getBiosecurity() != null) existingEvaluation.setBiosecurity(data.getBiosecurity());
    if (data.getEthics() != null) existingEvaluation.setEthics(data.getEthics());
    if (data.getConcept() != null) existingEvaluation.setConcept(data.getConcept());
    if (data.getObservations() != null && !data.getObservations().isBlank()) {
      existingEvaluation.setObservations(data.getObservations().trim());
    }

    evaluationRepository.save(existingEvaluation);
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting evaluation with id: {}", id);
    EvaluationEntity evaluationEntity = this.findById(id);

    UUID userId = findAuthenticatedUserId();
    evaluationEntity.setDeletedBy(userId);
    evaluationEntity.setDeleted(true);
    evaluationEntity.setDeletedAt(LocalDateTime.now());

    this.evaluationRepository.save(evaluationEntity);
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
