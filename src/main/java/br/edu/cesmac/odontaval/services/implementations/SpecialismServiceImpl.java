package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.repositories.SpecialismRepository;
import br.edu.cesmac.odontaval.services.AuthenticatedUserService;
import br.edu.cesmac.odontaval.services.SpecialismService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialismServiceImpl implements SpecialismService {
  private final SpecialismRepository specialismRepository;
  private final AuthenticatedUserService authenticatedUserService;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void insert(SpecialismEntity data) {
    log.info("Inserting a new specialism");
    boolean specialismAlreadyExists = findByName(data.getName().trim()).isPresent();
    if (specialismAlreadyExists)
      throw new OdontAvalException("Essa especialidade já existe", HttpStatus.NOT_FOUND);

    SpecialismEntity specialismEntity = new SpecialismEntity();
    specialismEntity.setName(data.getName().trim());
    specialismEntity.setDescription(data.getDescription().trim());
    specialismEntity.setCreatedAt(LocalDateTime.now());
    specialismEntity.setDeleted(false);

    this.specialismRepository.save(specialismEntity);
  }

  @Override
  public List<SpecialismEntity> findAll() {
    log.info("Finding all specialisms");
    return this.specialismRepository.findAll();
  }

  @Override
  public SpecialismEntity findById(Long id) {
    log.info("Finding a specialism by id: {}", id);
    return this.specialismRepository
        .findById(id)
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Nenhuma especialidade encontrada para esse id", HttpStatus.NOT_FOUND));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void update(Long id, SpecialismEntity data) {
    log.info("Updating a specialism with id: {}", id);
    SpecialismEntity existingSpecialism = this.findById(id);

    if (data.getName() != null) existingSpecialism.setName(data.getName().trim());
    if (data.getDescription() != null)
      existingSpecialism.setDescription(data.getDescription().trim());

    this.specialismRepository.save(existingSpecialism);
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting a specialism by id: {}", id);
    SpecialismEntity specialismEntity = this.findById(id);

    UUID userId = findAuthenticatedUserId();
    specialismEntity.setDeletedBy(userId);
    specialismEntity.setDeleted(true);
    specialismEntity.setDeletedAt(LocalDateTime.now());

    this.specialismRepository.save(specialismEntity);
  }

  private Optional<SpecialismEntity> findByName(String name) {
    return specialismRepository.findByNameIgnoreCase(name);
  }

  private UUID findAuthenticatedUserId() {
    return authenticatedUserService
        .findCurrentUserId()
        .orElseThrow(
            () ->
                new OdontAvalException(
                    "Ocorreu um erro ao buscar o usuário autenticado",
                    HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
