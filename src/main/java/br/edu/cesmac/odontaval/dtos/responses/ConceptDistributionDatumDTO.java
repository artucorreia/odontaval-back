package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConceptDistributionDatumDTO {
  private String name;
  private Long value;
  private Double pct;
  private String color;
  private Double minScore;
  private Double maxScore;
}
