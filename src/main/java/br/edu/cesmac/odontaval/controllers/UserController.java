package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.UserConstant;
import br.edu.cesmac.odontaval.controllers.mappers.UserMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.AdminPasswordResetRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.AdminUserInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.PasswordUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.UserRoleUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.UserUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.UserResponseDTO;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findAll() {
    List<UserEntity> users = userService.findAll();
    List<UserResponseDTO> data = userMapper.userEntitiesToResponseDTOs(users);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, data));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findByRole(@RequestParam String role) {
    List<UserEntity> users = userService.findByRole(role);
    List<UserResponseDTO> data = userMapper.userEntitiesToResponseDTOs(users);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, data));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<UserResponseDTO>> findById(@PathVariable UUID id) {
    UserEntity user = userService.findById(id);
    UserResponseDTO data = userMapper.userEntityToResponseDTO(user);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, data));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Void>> insert(
      @Valid @RequestBody AdminUserInsertRequestDTO dto) {
    UserEntity newUser = new UserEntity();
    newUser.setName(dto.getName());
    newUser.setEmail(dto.getEmail());
    newUser.setPassword(dto.getPassword());
    userService.insertWithRole(newUser, dto.getRole());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ResponseDTO<>(
                true, "Usuário criado com sucesso", HttpStatus.CREATED.value(), null));
  }

  @PutMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<UserResponseDTO>> update(
      @PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO dto) {
    UserEntity updated = userService.update(id, dto);
    UserResponseDTO data = userMapper.userEntityToResponseDTO(updated);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, data));
  }

  @PutMapping(
      value = "/{id}/password",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Void>> updatePassword(
      @PathVariable UUID id, @Valid @RequestBody PasswordUpdateRequestDTO dto) {
    userService.updatePassword(id, dto);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, null));
  }

  @PutMapping(
      value = "/{id}/role",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Void>> updateRole(
      @PathVariable UUID id, @Valid @RequestBody UserRoleUpdateRequestDTO dto) {
    userService.updateRole(id, dto.getRole());
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, null));
  }

  @PutMapping(
      value = "/{id}/admin-reset-password",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Void>> adminResetPassword(
      @PathVariable UUID id, @Valid @RequestBody AdminPasswordResetRequestDTO dto) {
    userService.resetPassword(id, dto.getNewPassword());
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, null));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}/reactivate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Void>> reactivate(@PathVariable UUID id) {
    userService.reactivate(id);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, null));
  }
}
