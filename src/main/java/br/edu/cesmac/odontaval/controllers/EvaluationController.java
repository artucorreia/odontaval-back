package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.controllers.mappers.EvaluationMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.EvaluationRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.EvaluationResponseDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final EvaluationMapper evaluationMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<EvaluationResponseDTO>> insert(@RequestBody EvaluationRequestDTO evaluationRequestDTO) {

        EvaluationEntity entityToSave = evaluationMapper.evaluationRequestDTOToEntity(evaluationRequestDTO);
        EvaluationEntity savedEntity = evaluationService.insert(entityToSave);

        EvaluationResponseDTO data = new EvaluationResponseDTO(
                savedEntity.getId(), savedEntity.getPunctuality(), savedEntity.getInstrumental(),
                savedEntity.getOrganizationOfServiceUnit(), savedEntity.getBiosecurity(),
                savedEntity.getEthics(), savedEntity.getConcept(), savedEntity.getObservations(),
                savedEntity.getStudent().getId(), savedEntity.getExam().getId()
        );

        ResponseDTO<EvaluationResponseDTO> response = new ResponseDTO<>(
                true, "Avaliação criada com sucesso", HttpStatus.CREATED.value(), data
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<List<EvaluationResponseDTO>>> findAll() {

        List<EvaluationEntity> evaluations = evaluationService.findAll();

        List<EvaluationResponseDTO> evaluationResponseDTOS = evaluations.stream()
                .map(eval -> new EvaluationResponseDTO(
                        eval.getId(), eval.getPunctuality(), eval.getInstrumental(),
                        eval.getOrganizationOfServiceUnit(), eval.getBiosecurity(),
                        eval.getEthics(), eval.getConcept(), eval.getObservations(),
                        eval.getStudent().getId(), eval.getExam().getId()
                )).toList();

        ResponseDTO<List<EvaluationResponseDTO>> response = new ResponseDTO<>(
                true, null, HttpStatus.OK.value(), evaluationResponseDTOS
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<EvaluationResponseDTO>> findById(@PathVariable Long id) {

        EvaluationEntity eval = evaluationService.findById(id);

        EvaluationResponseDTO evaluationResponseDTO = new EvaluationResponseDTO(
                eval.getId(), eval.getPunctuality(), eval.getInstrumental(),
                eval.getOrganizationOfServiceUnit(), eval.getBiosecurity(),
                eval.getEthics(), eval.getConcept(), eval.getObservations(),
                eval.getStudent().getId(), eval.getExam().getId()
        );

        ResponseDTO<EvaluationResponseDTO> response = new ResponseDTO<>(
                true, null, HttpStatus.OK.value(), evaluationResponseDTO
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<EvaluationResponseDTO>> update(
            @PathVariable Long id, @RequestBody EvaluationRequestDTO evaluationRequestDTO) {

        EvaluationEntity entityToUpdate = evaluationMapper.evaluationRequestDTOToEntity(evaluationRequestDTO);
        EvaluationEntity updatedEntity = evaluationService.update(id, entityToUpdate);

        EvaluationResponseDTO data = new EvaluationResponseDTO(
                updatedEntity.getId(), updatedEntity.getPunctuality(), updatedEntity.getInstrumental(),
                updatedEntity.getOrganizationOfServiceUnit(), updatedEntity.getBiosecurity(),
                updatedEntity.getEthics(), updatedEntity.getConcept(), updatedEntity.getObservations(),
                updatedEntity.getStudent().getId(), updatedEntity.getExam().getId()
        );

        ResponseDTO<EvaluationResponseDTO> response = new ResponseDTO<>(
                true, "Avaliação atualizada com sucesso", HttpStatus.OK.value(), data
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        evaluationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}