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
public class AdminUserInsertRequestDTO {

  @NotBlank
  @Size(min = 2, max = 50)
  private String name;

  @NotBlank
  @Size(min = 12, max = 60)
  @Email
  private String email;

  @NotBlank
  @Size(min = 8, max = 50)
  private String password;

  @NotBlank
  private String role;
}
