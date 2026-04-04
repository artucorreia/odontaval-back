package br.edu.cesmac.odontaval.services.implementations;

import java.util.List;
import java.util.Set;

import br.edu.cesmac.odontaval.models.RoleEntity;
import br.edu.cesmac.odontaval.repositories.RoleRepository;
import br.edu.cesmac.odontaval.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Set<RoleEntity> findByName(List<String> names) {
    log.info("Finding role by names: {}", names);
    return roleRepository.findByNameInIgnoreCase(names);
  }
}
