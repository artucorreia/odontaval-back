package br.edu.cesmac.odontaval.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationDTO {
  @Email(message = "O e-mail precisa ter um formato válido")
  @NotBlank(message = "O e-mail é obrigatório")
  @Size(min = 12, max = 60, message = "O e-mail deve ter entre 12 e 60 caracteres")
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 8, max = 50, message = "A senha deve ter entre 8 e 50 carecteres")
  private String password;
}
