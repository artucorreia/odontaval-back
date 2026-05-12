package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordRequestDTO {
  @NotNull(message = "O id do usuário é obrigatório")
  private UUID userId;

  @NotBlank(message = "O token de recuperação é obrigatório")
  private String recoveryToken;

  @NotBlank(message = "A nova senha é obrigatória")
  @Size(min = 8, max = 50, message = "A nova senha deve ter entre 8 e 50 caracteres")
  private String newPassword;
}
