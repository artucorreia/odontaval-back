package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.controllers.mappers.ExamMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.ExamRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.ExamResponseDTO;
import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.services.ExamService;
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
  public ResponseEntity<ResponseDTO<Object>> insert(@RequestBody ExamRequestDTO examRequestDTO) {
    ExamEntity examEntity = examMapper.examRequestDTOToEntity(examRequestDTO);
    examService.insert(examEntity);

    ResponseDTO<Object> response = new ResponseDTO<>(true, "Exame criado com sucesso", 201, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<ExamResponseDTO>>> findAll() {

    List<ExamEntity> exams = examService.findAll();

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
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), examResponseDTOS);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<ExamResponseDTO>> findById(@PathVariable Long id) {

    ExamEntity exam = examService.findById(id);

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
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), examResponseDTO);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<ResponseDTO>> update(
      @PathVariable Long id, @RequestBody ExamRequestDTO examRequestDTO) {

    ExamEntity examEntity = examMapper.examRequestDTOToEntity(examRequestDTO);
    examService.update(id, examEntity);

    ResponseDTO<ResponseDTO> response =
        new ResponseDTO<>(true, "Exame atualizado com sucesso", HttpStatus.OK.value(), null);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    examService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
