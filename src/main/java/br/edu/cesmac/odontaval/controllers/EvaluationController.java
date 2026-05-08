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
import java.util.UUID;

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
  public ResponseEntity<ResponseDTO<List<EvaluationResponseDTO>>> findAll(
      @RequestParam(required = false) UUID studentId) {

    List<EvaluationEntity> evaluations = studentId != null
        ? evaluationService.findByStudentId(studentId)
        : evaluationService.findAll();

    List<EvaluationResponseDTO> data =
        evaluationMapper.evaluationEntitiesToResponseDTOs(evaluations);

    ResponseDTO<List<EvaluationResponseDTO>> response =
        new ResponseDTO<>(true, null, EvaluationConstant.STATUS_200, data);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<EvaluationResponseDTO>> findById(@PathVariable Long id) {

    EvaluationEntity evaluationEntity = evaluationService.findById(id);

    EvaluationResponseDTO dto = evaluationMapper.evaluationEntityToResponseDTO(evaluationEntity);

    ResponseDTO<EvaluationResponseDTO> response =
        new ResponseDTO<>(true, null, EvaluationConstant.STATUS_200, dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> update(
      @PathVariable Long id,
      @Valid @RequestBody EvaluationUpdateRequestDTO evaluationUpdateRequestDTO) {

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
