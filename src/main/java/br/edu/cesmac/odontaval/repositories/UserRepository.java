package br.edu.cesmac.odontaval.repositories;

import br.edu.cesmac.odontaval.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmailIgnoreCase(String email);

  @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE LOWER(r.name) = LOWER(:roleName) AND SIZE(u.roles) = 1 AND u.deleted = false")
  List<UserEntity> findByExclusiveRoleIgnoreCase(@Param("roleName") String roleName);

  long countByRolesNameIgnoreCase(String roleName);

  List<UserEntity> findAllByOrderByCreatedAtDesc();
}
