package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordUpdateRequestDTO {
  @NotBlank(message = "A senha atual é obrigatória")
  private String currentPassword;

  @NotBlank(message = "A nova senha é obrigatória")
  @Size(min = 8, max = 50, message = "A senha deve ter entre 8 e 50 caracteres")
  private String newPassword;
}
