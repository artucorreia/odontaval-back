package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationInsertRequestDTO {

  @Size(min = 3, max = 50, message = "O título deve ter entre 3 e 50 caracteres")
  private String title;

  @NotNull(message = "A nota de pontualidade é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de pontualidade deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de pontualidade deve ser no máximo 0")
  private Double punctuality;

  @NotNull(message = "A nota de instrumental é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de instrumental deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de instrumental deve ser no máximo 0")
  private Double instrumental;

  @NotNull(message = "A nota de organização do box é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de organização do box deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de organização do box deve ser no máximo 0")
  private Double boxOrganization;

  @NotNull(message = "A nota de biossegurança é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de biossegurança deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de biossegurança deve ser no máximo 0")
  private Double biosecurity;

  @NotNull(message = "A nota de ética é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de ética deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de ética deve ser no máximo 0")
  private Double ethics;

  @NotNull(message = "A nota de conceito é obrigatória")
  @DecimalMin(value = "-10.0", message = "A nota de conceito deve ser no mínimo -10")
  @DecimalMax(value = "0.0", message = "A nota de conceito deve ser no máximo 0")
  private Double concept;

  @NotNull(message = "A nota final é obrigatória")
  @DecimalMin(value = "0.0", message = "A nota final deve ser no mínimo 0")
  @DecimalMax(value = "10.0", message = "A nota final deve ser no máximo 10")
  private Double grade;

  @Size(max = 200, message = "A observação deve ter no máximo 200 caracteres")
  private String observations;

  @Pattern(regexp = "^AV[1-3]$", message = "O número da avaliação deve ser AV1, AV2 ou AV3")
  private String evaluationNumber;

  @NotNull(message = "A data é obrigatória")
  private LocalDate date;

  @Pattern(
      regexp = "^\\d{4}\\.[12]$",
      message = "O semestre acadêmico deve estar no formato AAAA.1 ou AAAA.2")
  private String academicSemester;

  @Size(max = 200, message = "Os objetivos devem ter no máximo 200 caracteres")
  private String goals;

  @Size(max = 20, message = "O box deve ter no máximo 20 caracteres")
  private String box;

  @Size(max = 50, message = "O procedimento realizado deve ter no máximo 50 caracteres")
  private String procedurePerformed;

  @NotNull(message = "O id do aluno é obrigatório")
  private UUID studentId;

  @NotNull(message = "O id da especialidade é obrigatório")
  @Min(value = 1, message = "O id da especialidade deve ser maior que 0")
  private Long specialismId;
}
