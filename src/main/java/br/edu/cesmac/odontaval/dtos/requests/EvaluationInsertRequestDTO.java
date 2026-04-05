package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationInsertRequestDTO {

  @NotNull(message = "A nota de pontualidade é obrigatória")
  @Min(value = 0, message = "A nota de pontualidade deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de pontualidade deve ser no máximo 10")
  private Double punctuality;

  @NotNull(message = "A nota de instrumental é obrigatória")
  @Min(value = 0, message = "A nota de instrumental deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de instrumental deve ser no máximo 10")
  private Double instrumental;

  @NotNull(message = "A nota de organização da unidade de serviço é obrigatória")
  @Min(value = 0, message = "A nota de organização da unidade de serviço deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de organização da unidade de serviço deve ser no máximo 10")
  private Double organizationOfServiceUnit;

  @NotNull(message = "A nota de biossegurança é obrigatória")
  @Min(value = 0, message = "A nota de biossegurança deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de biossegurança deve ser no máximo 10")
  private Double biosecurity;

  @NotNull(message = "A nota de ética é obrigatória")
  @Min(value = 0, message = "A nota de ética deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de ética deve ser no máximo 10")
  private Double ethics;

  @NotNull(message = "A nota de conceito é obrigatória")
  @Min(value = 0, message = "A nota de conceito deve ser no mínimo 0")
  @Max(value = 10, message = "A nota de conceito deve ser no máximo 10")
  private Double concept;

  @Size(min = 5, max = 500, message = "A observação deve ter entre 5 e 500 caracteres")
  private String observations;

  @NotNull(message = "O id da avaliação é obrigatório")
  private Long examId;

  @NotNull(message = "O id da aluno é obrigatório")
  private UUID studentId;
}
