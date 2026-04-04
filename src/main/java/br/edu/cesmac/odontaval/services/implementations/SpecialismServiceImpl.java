package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.dtos.requests.SpecialismRequestDTO;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.repositories.SpecialismRepository;
import br.edu.cesmac.odontaval.services.SpecialismService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SpecialismServiceImpl implements SpecialismService {
    private final SpecialismRepository specialismRepository;

    public SpecialismServiceImpl(SpecialismRepository specialismRepository) {
        this.specialismRepository = specialismRepository;
    }

    @Override
    @Transactional
    public SpecialismEntity createSpecialism(SpecialismRequestDTO data) {
        SpecialismEntity specialismEntity = new SpecialismEntity();

        specialismEntity.setName(data.name());
        specialismEntity.setDescription(data.description());
        specialismEntity.setDeleted(false);

        return this.specialismRepository.save(specialismEntity);
    }

    @Override
    public List<SpecialismEntity> getAllSpecialisms() {
        return this.specialismRepository.findAll();
    }

    @Override
    public SpecialismEntity getSpecialismById(Long id) {
        return this.specialismRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialism not found"));
    }

    @Override
    public SpecialismEntity updateSpecialism(Long id, SpecialismRequestDTO data) {
        SpecialismEntity existingSpecialism = this.getSpecialismById(id);

        existingSpecialism.setName(data.name());
        existingSpecialism.setDescription(data.description());

        return this.specialismRepository.save(existingSpecialism);
    }

    @Override
    public void deleteSpecialism(Long id) {
        SpecialismEntity specialismEntity = this.getSpecialismById(id);

        specialismEntity.setDeleted(true);
        specialismEntity.setDeletedAt(LocalDateTime.now());

        this.specialismRepository.save(specialismEntity);
    }
}
