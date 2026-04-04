package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.repositories.SpecialismRepository;
import br.edu.cesmac.odontaval.services.SpecialismService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialismServiceImpl implements SpecialismService {
  private final SpecialismRepository specialismRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void insert(SpecialismEntity data) {
    SpecialismEntity specialismEntity = new SpecialismEntity();

    specialismEntity.setName(data.getName().trim());
    specialismEntity.setDescription(data.getDescription().trim());
    specialismEntity.setCreatedAt(LocalDateTime.now());
    specialismEntity.setDeleted(false);

    this.specialismRepository.save(specialismEntity);
  }

  @Override
  public List<SpecialismEntity> findAll() {
    return this.specialismRepository.findAll();
  }

  @Override
  public SpecialismEntity findById(Long id) {
    return this.specialismRepository
        .findById(id)
        .orElseThrow(
            () -> new OdontAvalException("Nenhuma especialidade encontrada para esse id", HttpStatus.NOT_FOUND));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void update(Long id, SpecialismEntity data) {
    SpecialismEntity existingSpecialism = this.findById(id);

    existingSpecialism.setName(data.getName().trim());
    existingSpecialism.setDescription(data.getDescription().trim());

    this.specialismRepository.save(existingSpecialism);
  }

  @Override
  public void delete(Long id) {
    SpecialismEntity specialismEntity = this.findById(id);

    specialismEntity.setDeleted(true);
    // todo: set deleted by property
    specialismEntity.setDeletedAt(LocalDateTime.now());

    this.specialismRepository.save(specialismEntity);
  }
}
