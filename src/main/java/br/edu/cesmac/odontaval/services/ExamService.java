package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.models.ExamEntity;

import java.util.List;

public interface ExamService {
    List<ExamEntity> findAll();

    ExamEntity findById(Long id);

    void insert(ExamEntity examEntity);

    void update(Long id, ExamEntity examEntity);

    void delete(Long id);
}
