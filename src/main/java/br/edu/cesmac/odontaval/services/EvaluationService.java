package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.EvaluationEntity;

import java.util.List;

public interface EvaluationService {
  void insert(EvaluationEntity evaluationEntity);

  List<EvaluationEntity> findAll();

  EvaluationEntity findById(Long id);

  void update(Long id, EvaluationEntity evaluationEntity);

  void delete(Long id);
}
