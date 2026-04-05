package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.controllers.mappers.UserMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.auth.AuthenticationDTO;
import br.edu.cesmac.odontaval.dtos.auth.RegisterDTO;
import br.edu.cesmac.odontaval.dtos.auth.TokenResponseDTO;
import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.security.CustomUserDetails;
import br.edu.cesmac.odontaval.security.services.GenerateToken;
import br.edu.cesmac.odontaval.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final GenerateToken generateToken;
  private final UserMapper userMapper;

  @PostMapping(
      value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<TokenResponseDTO>> login(
      @RequestBody @Valid AuthenticationDTO authenticationDTO) {
    UserEntity user = userService.findByEmail(authenticationDTO.getEmail().trim());
    if (user.getDeleted()) throw new OdontAvalException("O usuário está inativo", HttpStatus.FORBIDDEN);

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(
            authenticationDTO.getEmail().trim(), authenticationDTO.getPassword());

    String token;
    try {
      Authentication auth = authenticationManager.authenticate(usernamePassword);
      CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
      token = generateToken.generate(customUserDetails.getUser().getId());
    } catch (AuthenticationException e) {
      throw new OdontAvalException("Senha incorreta", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      throw new OdontAvalException("Erro durante a geração do token", HttpStatus.BAD_REQUEST);
    }

    TokenResponseDTO tokenResponseDTO =
        new TokenResponseDTO(user.getId(), token);
    ResponseDTO<TokenResponseDTO> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), tokenResponseDTO);
    return ResponseEntity.ok(response);
  }

  @PostMapping(
      value = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<Object>> register(
      @RequestBody @Valid RegisterDTO registerDTO) {
    UserEntity user = userMapper.registerDTOToEntity(registerDTO);
    userService.insert(user);
    ResponseDTO<Object> response =
        new ResponseDTO<>(true, "Usuário criado com sucesso", HttpStatus.CREATED.value(), null);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
