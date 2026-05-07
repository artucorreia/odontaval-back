package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.UserConstant;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.RoleResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.UserResponseDTO;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findByRole(@RequestParam String role) {
    List<UserEntity> users = userService.findByRole(role);

    List<UserResponseDTO> data =
        users.stream()
            .map(
                user ->
                    new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                            .map(r -> new RoleResponseDTO(r.getId(), r.getName()))
                            .collect(Collectors.toSet()),
                        user.getCreatedAt()))
            .toList();

    return ResponseEntity.ok(new ResponseDTO<>(true, null, UserConstant.STATUS_200, data));
  }
}
