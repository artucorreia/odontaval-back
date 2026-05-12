package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.SpecialismEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialismRepository extends JpaRepository<SpecialismEntity, Long> {
  Optional<SpecialismEntity> findByNameIgnoreCase(String name);
}
