package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecentEvaluationResponseDTO {
  private Long id;
  private String title;
  private String specialismName;
  private String academicSemester;
  private LocalDate date;
}
