package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequestDTO {
  @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
  private String name;

  @Email(message = "O e-mail deve estar em um formato válido")
  @Size(min = 12, max = 60, message = "O e-mail deve ter entre 12 e 60 caracteres")
  private String email;
}
