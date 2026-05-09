package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.SpecialismConstant;
import br.edu.cesmac.odontaval.controllers.mappers.SpecialismMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.SpecialismInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.SpecialismUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.SpecialismResponseDTO;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.services.SpecialismService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/specialisms")
@RequiredArgsConstructor
public class SpecialismController {

  private final SpecialismService specialismService;
  private final SpecialismMapper specialismMapper;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> insert(
      @Valid @RequestBody SpecialismInsertRequestDTO specialismInsertRequestDTO) {

    SpecialismEntity specialismEntity =
        specialismMapper.specialismInsertRequestDTOToEntity(specialismInsertRequestDTO);
    specialismService.insert(specialismEntity);

    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true, SpecialismConstant.INSERT_MESSAGE, SpecialismConstant.INSERT_STATUS, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<SpecialismResponseDTO>>> findAll() {

    List<SpecialismEntity> specialisms = specialismService.findAll();

    List<SpecialismResponseDTO> data =
        specialismMapper.specialismEntitiesToResponseDTOs(specialisms);

    ResponseDTO<List<SpecialismResponseDTO>> response =
        new ResponseDTO<>(true, null, SpecialismConstant.STATUS_200, data);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<SpecialismResponseDTO>> findById(@PathVariable Long id) {

    SpecialismEntity entity = specialismService.findById(id);

    SpecialismResponseDTO dto = specialismMapper.specialismEntityToResponseDTO(entity);

    ResponseDTO<SpecialismResponseDTO> response =
        new ResponseDTO<>(true, null, SpecialismConstant.STATUS_200, dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> update(
      @PathVariable Long id,
      @Valid @RequestBody SpecialismUpdateRequestDTO specialismUpdateRequestDTO) {

    SpecialismEntity specialismEntity =
        specialismMapper.specialismUpdateRequestDTOToEntity(specialismUpdateRequestDTO);
    specialismService.update(id, specialismEntity);

    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true, SpecialismConstant.UPDATE_MESSAGE, SpecialismConstant.UPDATE_STATUS, null);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    specialismService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}/reactivate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> reactivate(@PathVariable Long id) {
    specialismService.reactivate(id);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, SpecialismConstant.STATUS_200, null));
  }
}
