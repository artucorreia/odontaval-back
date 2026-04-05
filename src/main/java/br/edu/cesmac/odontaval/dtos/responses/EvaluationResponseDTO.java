package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationResponseDTO {
  private Long id;
  private Double punctuality;
  private Double instrumental;
  private Double organizationOfServiceUnit;
  private Double biosecurity;
  private Double ethics;
  private Double concept;
  private String observations;
  private UUID studentId;
  private Long examId;
}
