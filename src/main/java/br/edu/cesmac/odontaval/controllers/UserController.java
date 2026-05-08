package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.UserConstant;
import br.edu.cesmac.odontaval.controllers.mappers.UserMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.UserResponseDTO;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.services.UserService;
import lombok.RequiredArgsConstructor;
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
}
