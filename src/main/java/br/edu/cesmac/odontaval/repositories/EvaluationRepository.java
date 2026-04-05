package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {}
