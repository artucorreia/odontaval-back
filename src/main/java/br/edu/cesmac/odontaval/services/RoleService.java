package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService {
  Set<RoleEntity> findByName(List<String> names);
}
