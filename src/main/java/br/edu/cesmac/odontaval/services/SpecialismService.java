package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.SpecialismEntity;
import java.util.List;

public interface SpecialismService {
  void insert(SpecialismEntity specialismEntity);

  List<SpecialismEntity> findAll();

  SpecialismEntity findById(Long id);

  void update(Long id, SpecialismEntity specialismEntity);

  void delete(Long id);

  void reactivate(Long id);
}
