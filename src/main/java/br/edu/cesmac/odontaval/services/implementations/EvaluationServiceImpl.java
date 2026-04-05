package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.services.EvaluationService;
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
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EvaluationEntity insert(EvaluationEntity data) {
        EvaluationEntity evaluationEntity = new EvaluationEntity();

        evaluationEntity.setPunctuality(data.getPunctuality());
        evaluationEntity.setInstrumental(data.getInstrumental());
        evaluationEntity.setOrganizationOfServiceUnit(data.getOrganizationOfServiceUnit());
        evaluationEntity.setBiosecurity(data.getBiosecurity());
        evaluationEntity.setEthics(data.getEthics());
        evaluationEntity.setConcept(data.getConcept());

        if (data.getObservations() != null) {
            evaluationEntity.setObservations(data.getObservations().trim());
        }

        evaluationEntity.setStudent(data.getStudent());
        evaluationEntity.setExam(data.getExam());

        evaluationEntity.setCreatedAt(LocalDateTime.now());
        evaluationEntity.setDeleted(false);

        return this.evaluationRepository.save(evaluationEntity);
    }

    @Override
    public List<EvaluationEntity> findAll() {
        return this.evaluationRepository.findAll();
    }

    @Override
    public EvaluationEntity findById(Long id) {
        return this.evaluationRepository.findById(id)
                .orElseThrow(() -> new OdontAvalException("Nenhuma avaliação encontrada para esse id", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EvaluationEntity update(Long id, EvaluationEntity data) {
        EvaluationEntity existingEvaluation = this.findById(id);

        existingEvaluation.setPunctuality(data.getPunctuality());
        existingEvaluation.setInstrumental(data.getInstrumental());
        existingEvaluation.setOrganizationOfServiceUnit(data.getOrganizationOfServiceUnit());
        existingEvaluation.setBiosecurity(data.getBiosecurity());
        existingEvaluation.setEthics(data.getEthics());
        existingEvaluation.setConcept(data.getConcept());

        if (data.getObservations() != null) {
            existingEvaluation.setObservations(data.getObservations().trim());
        }

        existingEvaluation.setStudent(data.getStudent());
        existingEvaluation.setExam(data.getExam());

        return this.evaluationRepository.save(existingEvaluation);
    }

    @Override
    public void delete(Long id) {
        EvaluationEntity evaluationEntity = this.findById(id);

        evaluationEntity.setDeleted(true);
        // todo: set deleted by property
        evaluationEntity.setDeletedAt(LocalDateTime.now());

        this.evaluationRepository.save(evaluationEntity);
    }
}