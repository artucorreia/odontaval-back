package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.dtos.requests.SpecialismRequestDTO;
import java.util.List;

public interface SpecialismService {
    SpecialismEntity createSpecialism(SpecialismRequestDTO data);

    List<SpecialismEntity> getAllSpecialisms();

    SpecialismEntity getSpecialismById(Long id);

    SpecialismEntity updateSpecialism(Long id, SpecialismRequestDTO data);

    void deleteSpecialism(Long id);
}