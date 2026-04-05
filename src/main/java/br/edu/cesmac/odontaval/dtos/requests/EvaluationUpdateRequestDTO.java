package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationUpdateRequestDTO {

  @Min(value = 0, message = "a nota de pontualidade deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de pontualidade deve ser no máximo 10")
  private Double punctuality;

  @Min(value = 0, message = "a nota de instrumental deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de instrumental deve ser no máximo 10")
  private Double instrumental;

  @Min(value = 0, message = "a nota de organização da unidade de serviço deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de organização da unidade de serviço deve ser no máximo 10")
  private Double organizationOfServiceUnit;

  @Min(value = 0, message = "a nota de biossegurança deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de biossegurança deve ser no máximo 10")
  private Double biosecurity;

  @Min(value = 0, message = "a nota de ética deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de ética deve ser no máximo 10")
  private Double ethics;

  @Min(value = 0, message = "a nota de conceito deve ser no mínimo 0")
  @Max(value = 10, message = "a nota de conceito deve ser no máximo 10")
  private Double concept;

  @Size(min = 5, max = 500, message = "a observação deve ter entre 5 e 500 caracteres")
  private String observations;
}
