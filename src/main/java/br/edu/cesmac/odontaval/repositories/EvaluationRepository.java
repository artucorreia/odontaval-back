package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
  long countByDeletedFalse();

  long countByDateAndDeletedFalse(LocalDate date);

  List<EvaluationEntity> findTop5ByDeletedFalseOrderByCreatedAtDesc();
}
