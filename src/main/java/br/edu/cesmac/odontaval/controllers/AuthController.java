package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.AuthConstant;
import br.edu.cesmac.odontaval.controllers.mappers.UserMapper;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.auth.AuthenticationDTO;
import br.edu.cesmac.odontaval.dtos.auth.RegisterDTO;
import br.edu.cesmac.odontaval.dtos.auth.TokenResponseDTO;
import br.edu.cesmac.odontaval.dtos.requests.ConfirmEmailRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.PasswordRecoveryRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.RefreshTokenRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.ResetPasswordRequestDTO;
import br.edu.cesmac.odontaval.exceptions.OdontAvalException;
import br.edu.cesmac.odontaval.models.RefreshTokenEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import br.edu.cesmac.odontaval.security.CustomUserDetails;
import br.edu.cesmac.odontaval.security.services.GenerateToken;
import br.edu.cesmac.odontaval.services.MailService;
import br.edu.cesmac.odontaval.services.MailTokenService;
import br.edu.cesmac.odontaval.services.RefreshTokenService;
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
  private final MailTokenService mailTokenService;
  private final MailService mailService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping(
      value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<TokenResponseDTO>> login(
      @RequestBody @Valid AuthenticationDTO authenticationDTO) {
    UserEntity user = userService.findByEmail(authenticationDTO.getEmail().trim());
    if (user.getDeleted())
      throw new OdontAvalException("O usuário está inativo", HttpStatus.FORBIDDEN);

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

    String userRole = userService.extractRoleName(user.getRoles());
    RefreshTokenEntity refreshToken = refreshTokenService.create(user);
    TokenResponseDTO tokenResponseDTO =
        new TokenResponseDTO(user.getId(), userRole, token, refreshToken.getToken());
    ResponseDTO<TokenResponseDTO> response =
        new ResponseDTO<>(true, null, AuthConstant.STATUS_200, tokenResponseDTO);
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
    String confirmToken = mailTokenService.createConfirmToken(user);
    mailService.sendConfirmMail(user.getEmail(), user.getId(), user.getName(), confirmToken);
    ResponseDTO<Object> response =
        new ResponseDTO<>(true, AuthConstant.REGISTER_MESSAGE, AuthConstant.REGISTER_STATUS, null);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping(
      value = "/password-recovery",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<Object>> passwordRecovery(
      @RequestBody @Valid PasswordRecoveryRequestDTO dto) {
    UserEntity user = userService.findByEmail(dto.getEmail().trim());
    if (user.getDeleted())
      throw new OdontAvalException("O usuário está inativo", HttpStatus.FORBIDDEN);

    String recoveryToken = mailTokenService.createPasswordRecoveryToken(user);
    mailService.sendPasswordRecoveryMail(user.getEmail(), user.getId(), recoveryToken);

    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true,
            AuthConstant.PASSWORD_RECOVERY_MESSAGE,
            AuthConstant.PASSWORD_RECOVERY_STATUS,
            null);
    return ResponseEntity.ok(response);
  }

  @PostMapping(
      value = "/reset-password",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<Object>> resetPassword(
      @RequestBody @Valid ResetPasswordRequestDTO dto) {
    mailTokenService.resetPassword(dto.getUserId(), dto.getRecoveryToken(), dto.getNewPassword());
    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true, AuthConstant.RESET_PASSWORD_MESSAGE, AuthConstant.RESET_PASSWORD_STATUS, null);
    return ResponseEntity.ok(response);
  }

  @PostMapping(
      value = "/confirm-email",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<Object>> confirmEmail(
      @RequestBody @Valid ConfirmEmailRequestDTO dto) {
    mailTokenService.confirmEmail(dto.getUserId(), dto.getConfirmToken());
    ResponseDTO<Object> response =
        new ResponseDTO<>(
            true,
            AuthConstant.CONFIRM_EMAIL_MESSAGE,
            AuthConstant.CONFIRM_EMAIL_STATUS,
            null);
    return ResponseEntity.ok(response);
  }

  @PostMapping(
      value = "/refresh",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ResponseDTO<TokenResponseDTO>> refresh(
      @RequestBody @Valid RefreshTokenRequestDTO dto) {
    RefreshTokenEntity newRefreshToken = refreshTokenService.rotate(dto.getRefreshToken());
    UserEntity user = newRefreshToken.getUser();
    String newAccessToken = generateToken.generate(user.getId());
    String userRole = userService.extractRoleName(user.getRoles());
    TokenResponseDTO tokenResponseDTO =
        new TokenResponseDTO(user.getId(), userRole, newAccessToken, newRefreshToken.getToken());
    ResponseDTO<TokenResponseDTO> response =
        new ResponseDTO<>(true, null, AuthConstant.STATUS_200, tokenResponseDTO);
    return ResponseEntity.ok(response);
  }
}
