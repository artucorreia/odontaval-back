package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
  long countByDeletedFalse();

  long countByDateAndDeletedFalse(LocalDate date);

  long countByDateBetweenAndDeletedFalse(LocalDate start, LocalDate end);

  List<EvaluationEntity> findTop5ByDeletedFalseOrderByCreatedAtDesc();

  List<EvaluationEntity> findByDeletedFalseOrderByCreatedAtDesc();

  List<EvaluationEntity> findByStudentIdAndDeletedFalseOrderByCreatedAtDesc(UUID studentId);

  List<EvaluationEntity> findByAcademicSemesterAndDeletedFalseOrderByDateAsc(String academicSemester);

  @Query("SELECT DISTINCT e.academicSemester FROM EvaluationEntity e WHERE e.deleted = false ORDER BY e.academicSemester DESC")
  List<String> findDistinctAcademicSemesters();

  List<EvaluationEntity> findAllByOrderByCreatedAtDesc();
}
