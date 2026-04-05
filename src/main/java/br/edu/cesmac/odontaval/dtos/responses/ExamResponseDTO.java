package br.edu.cesmac.odontaval.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamResponseDTO {
  private Long id;
  private String title;
  private LocalDate date;
  private String academicSemester;
  private String goals;
  private String serviceUnit;
  private String procedurePerformed;
  private UUID professorId;
  private Long specialismId;
}
