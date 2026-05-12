package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.Email;
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
public class PasswordRecoveryRequestDTO {
  @Email(message = "O e-mail precisa ter um formato válido")
  @NotBlank(message = "O e-mail é obrigatório")
  @Size(min = 12, max = 60, message = "O e-mail deve ter entre 12 e 60 caracteres")
  private String email;
}
