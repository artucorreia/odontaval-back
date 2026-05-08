package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassAveragesResponseDTO {
  private Double punctuality;
  private Double instrumental;
  private Double boxOrganization;
  private Double biosecurity;
  private Double ethics;
  private Double concept;
}
