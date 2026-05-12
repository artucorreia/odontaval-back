package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmEmailRequestDTO {
  @NotNull(message = "O id do usuário é obrigatório")
  private UUID userId;

  @NotBlank(message = "O token de confirmação é obrigatório")
  private String confirmToken;
}
