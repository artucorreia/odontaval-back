package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.repositories.ExamRepository;
import br.edu.cesmac.odontaval.services.ExamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<ExamEntity> findAll() {
        return examRepository.findAll();
    }

    @Override
    public ExamEntity findById(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    @Override
    public void insert(ExamEntity examEntity) {

        ExamEntity exam = new ExamEntity();

        exam.setTitle(examEntity.getTitle().trim());
        exam.setDate(examEntity.getDate());

        if (examEntity.getAcademicSemester() != null) exam.setAcademicSemester(examEntity.getAcademicSemester());
        if (examEntity.getGoals() != null) exam.setGoals(examEntity.getGoals());
        if (examEntity.getProcedurePerformed() != null) exam.setProcedurePerformed(examEntity.getProcedurePerformed());
        if(examEntity.getServiceUnit() != null) exam.setServiceUnit(examEntity.getServiceUnit());

        exam.setProfessor(examEntity.getProfessor());
        exam.setSpecialism(examEntity.getSpecialism());
        exam.setCreatedAt(examEntity.getCreatedAt());
        exam.setDeleted(false);

        examRepository.save(exam);

    }

    @Override
    public void update(Long id, ExamEntity examEntity) {
        ExamEntity existingExam = this.findById(id);

        existingExam.setTitle(examEntity.getTitle().trim());
        existingExam.setDate(examEntity.getDate());

        if (examEntity.getAcademicSemester() != null) existingExam.setAcademicSemester(examEntity.getAcademicSemester());
        if (examEntity.getGoals() != null) existingExam.setGoals(examEntity.getGoals());
        if (examEntity.getProcedurePerformed() != null) existingExam.setProcedurePerformed(examEntity.getProcedurePerformed());
        if(examEntity.getServiceUnit() != null) existingExam.setServiceUnit(examEntity.getServiceUnit());

        existingExam.setProfessor(examEntity.getProfessor());
        existingExam.setSpecialism(examEntity.getSpecialism());
        existingExam.setCreatedAt(examEntity.getCreatedAt());
        existingExam.setDeleted(false);

        examRepository.save(existingExam);
    }

    @Override
    public void delete(Long id) {
        ExamEntity examEntity = this.findById(id);

        examEntity.setDeleted(true);
        examEntity.setDeletedAt(examEntity.getCreatedAt());

        examRepository.save(examEntity);
    }
}
