package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.ExamConstant;
import br.edu.cesmac.odontaval.controllers.mappers.ExamMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.ExamInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.ExamUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.ExamResponseDTO;
import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.services.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
  private final ExamService examService;
  private final ExamMapper examMapper;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> insert(
      @Valid @RequestBody ExamInsertRequestDTO examInsertRequestDTO) {
    ExamEntity examEntity = examMapper.examInsertRequestDTOToEntity(examInsertRequestDTO);
    examService.insert(examEntity);

    ResponseDTO<Object> response =
        new ResponseDTO<>(true, ExamConstant.INSERT_MESSAGE, ExamConstant.INSERT_STATUS, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<ExamResponseDTO>>> findAll() {

    List<ExamEntity> exams = examService.findAll();

    // todo: use mapper
    List<ExamResponseDTO> examResponseDTOS =
        exams.stream()
            .map(
                exam ->
                    new ExamResponseDTO(
                        exam.getId(),
                        exam.getTitle(),
                        exam.getDate(),
                        exam.getAcademicSemester(),
                        exam.getGoals(),
                        exam.getServiceUnit(),
                        exam.getProcedurePerformed(),
                        exam.getProfessor().getId(),
                        exam.getSpecialism().getId()))
            .toList();

    ResponseDTO<List<ExamResponseDTO>> response =
        new ResponseDTO<>(true, null, ExamConstant.STATUS_200, examResponseDTOS);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<ExamResponseDTO>> findById(@PathVariable Long id) {

    ExamEntity exam = examService.findById(id);

    // todo: use mapper
    ExamResponseDTO examResponseDTO =
        new ExamResponseDTO(
            exam.getId(), // <-- Added the missing ID right here!
            exam.getTitle(),
            exam.getDate(),
            exam.getAcademicSemester(),
            exam.getGoals(),
            exam.getServiceUnit(),
            exam.getProcedurePerformed(),
            exam.getProfessor().getId(),
            exam.getSpecialism().getId());

    ResponseDTO<ExamResponseDTO> response =
        new ResponseDTO<>(true, null, ExamConstant.STATUS_200, examResponseDTO);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> update(
      @PathVariable Long id, @Valid @RequestBody ExamUpdateRequestDTO examUpdateRequestDTO) {

    ExamEntity examEntity = examMapper.examUpdateRequestDTOToEntity(examUpdateRequestDTO);
    examService.update(id, examEntity);

    ResponseDTO<Object> response =
        new ResponseDTO<>(true, ExamConstant.UPDATE_MESSAGE, ExamConstant.UPDATE_STATUS, null);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    examService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
