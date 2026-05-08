package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriteriaComparisonDatumDTO {
  private String label;
  private Double avg;
  private Boolean isBest;
  private Boolean isWorst;
}
