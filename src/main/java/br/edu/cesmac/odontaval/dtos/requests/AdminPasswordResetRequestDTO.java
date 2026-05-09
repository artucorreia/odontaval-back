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
public class AdminPasswordResetRequestDTO {

  @NotBlank
  @Size(min = 8, max = 50)
  private String newPassword;
}
