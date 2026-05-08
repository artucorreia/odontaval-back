package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.EvaluationEntity;

import java.util.List;
import java.util.UUID;

public interface EvaluationService {
  void insert(EvaluationEntity evaluationEntity);

  List<EvaluationEntity> findAll();

  List<EvaluationEntity> findByStudentId(UUID studentId);

  EvaluationEntity findById(Long id);

  void update(Long id, EvaluationEntity evaluationEntity);

  void delete(Long id);
}
