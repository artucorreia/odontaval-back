package br.edu.cesmac.odontaval.repositories;

import java.util.List;
import java.util.Set;

import br.edu.cesmac.odontaval.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Set<RoleEntity> findByNameInIgnoreCase(List<String> names);
}
