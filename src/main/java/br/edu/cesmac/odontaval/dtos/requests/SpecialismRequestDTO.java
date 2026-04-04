package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SpecialismRequestDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
  private String name;

  @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
  private String description;
}
