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
public class SpecialismInsertRequestDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
  private String name;

  @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
  private String description;
}
