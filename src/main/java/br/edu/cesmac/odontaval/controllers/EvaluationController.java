package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.EvaluationConstant;
import br.edu.cesmac.odontaval.controllers.mappers.EvaluationMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.EvaluationInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.EvaluationUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.EvaluationResponseDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.services.EvaluationService;
import jakarta.validation.Valid;
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

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> insert(
      @Valid @RequestBody EvaluationInsertRequestDTO evaluationInsertRequestDTO) {

    EvaluationEntity evaluationEntity =
        evaluationMapper.evaluationInsertRequestDTOToEntity(evaluationInsertRequestDTO);
    evaluationService.insert(evaluationEntity);

    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true, EvaluationConstant.INSERT_MESSAGE, EvaluationConstant.INSERT_STATUS, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<EvaluationResponseDTO>>> findAll() {

    List<EvaluationEntity> evaluations = evaluationService.findAll();

    List<EvaluationResponseDTO> evaluationResponseDTOS =
        evaluations.stream()
            .map(
                eval ->
                    new EvaluationResponseDTO(
                        eval.getId(),
                        eval.getPunctuality(),
                        eval.getInstrumental(),
                        eval.getOrganizationOfServiceUnit(),
                        eval.getBiosecurity(),
                        eval.getEthics(),
                        eval.getConcept(),
                        eval.getObservations(),
                        eval.getStudent().getId(),
                        eval.getExam().getId()))
            .toList();

    ResponseDTO<List<EvaluationResponseDTO>> response =
        new ResponseDTO<>(true, null, EvaluationConstant.STATUS_200, evaluationResponseDTOS);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<EvaluationResponseDTO>> findById(@PathVariable Long id) {

    EvaluationEntity evaluationEntity = evaluationService.findById(id);

    EvaluationResponseDTO evaluationResponseDTO =
        new EvaluationResponseDTO(
            evaluationEntity.getId(),
            evaluationEntity.getPunctuality(),
            evaluationEntity.getInstrumental(),
            evaluationEntity.getOrganizationOfServiceUnit(),
            evaluationEntity.getBiosecurity(),
            evaluationEntity.getEthics(),
            evaluationEntity.getConcept(),
            evaluationEntity.getObservations(),
            evaluationEntity.getStudent().getId(),
            evaluationEntity.getExam().getId());

    ResponseDTO<EvaluationResponseDTO> response =
        new ResponseDTO<>(true, null, EvaluationConstant.STATUS_200, evaluationResponseDTO);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> update(
      @PathVariable Long id, @Valid @RequestBody EvaluationUpdateRequestDTO evaluationUpdateRequestDTO) {

    EvaluationEntity entityToUpdate =
        evaluationMapper.evaluationUpdateRequestDTOToEntity(evaluationUpdateRequestDTO);
    evaluationService.update(id, entityToUpdate);

    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true, EvaluationConstant.UPDATE_MESSAGE, EvaluationConstant.UPDATE_STATUS, null);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    evaluationService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
