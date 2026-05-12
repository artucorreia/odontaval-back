package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequestDTO {
  @NotBlank(message = "O refresh token é obrigatório")
  private String refreshToken;
}
