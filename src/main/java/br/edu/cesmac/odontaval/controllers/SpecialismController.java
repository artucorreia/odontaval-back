package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.dtos.requests.SpecialismRequestDTO;
import br.edu.cesmac.odontaval.DTOs.responses.SpecialismResponseDTO;
import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.services.SpecialismService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/specialisms")
public class SpecialismController {

    private final SpecialismService specialismService;

    public SpecialismController(SpecialismService specialismService) {
        this.specialismService = specialismService;
    }

    @PostMapping
    public ResponseEntity<SpecialismResponseDTO> createSpecialism(@RequestBody SpecialismRequestDTO data) {
        SpecialismEntity savedSpecialismEntity = specialismService.createSpecialism(data);

        SpecialismResponseDTO specialismResponseDTO = new SpecialismResponseDTO(
           savedSpecialismEntity.getId(),
           savedSpecialismEntity.getName(),
           savedSpecialismEntity.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(specialismResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<SpecialismResponseDTO>> getAll() {
        List<SpecialismEntity> specialisms = specialismService.getAllSpecialisms();
        List<SpecialismResponseDTO> specialismResponseDTOS = specialisms.stream()
                .map(specialism -> new SpecialismResponseDTO(specialism.getId(), specialism.getName(), specialism.getDescription()))
                .toList();

        return ResponseEntity.ok(specialismResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialismResponseDTO> getById(Long id) {
        SpecialismEntity entity = specialismService.getSpecialismById(id);

        SpecialismResponseDTO responseDTO = new SpecialismResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription());

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialismResponseDTO> updateSpecialism(@PathVariable Long id, @RequestBody SpecialismRequestDTO data) {
        SpecialismEntity updatedEntity = specialismService.updateSpecialism(id, data);

        SpecialismResponseDTO responseDTO = new SpecialismResponseDTO(
                updatedEntity.getId(),
                updatedEntity.getName(),
                updatedEntity.getDescription()
        );
        return ResponseEntity.ok(responseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialism(@PathVariable Long id) {
        specialismService.deleteSpecialism(id);
        return ResponseEntity.noContent().build();
    }
}
