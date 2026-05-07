package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
  long countByDateAndDeletedFalse(LocalDate date);

  List<ExamEntity> findTop5ByDeletedFalseOrderByCreatedAtDesc();
}
