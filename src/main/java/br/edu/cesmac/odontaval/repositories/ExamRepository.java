package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {}
