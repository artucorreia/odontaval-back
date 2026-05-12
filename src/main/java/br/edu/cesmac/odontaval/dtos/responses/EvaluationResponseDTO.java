package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationResponseDTO {
  private Long id;
  private String title;
  private Double punctuality;
  private Double instrumental;
  private Double boxOrganization;
  private Double biosecurity;
  private Double ethics;
  private Double concept;
  private Double grade;
  private String observations;
  private String evaluationNumber;
  private LocalDate date;
  private String academicSemester;
  private String goals;
  private String box;
  private String procedurePerformed;
  private UUID professorId;
  private String professorName;
  private UUID studentId;
  private String studentName;
  private String studentEmail;
  private Long specialismId;
  private String specialismName;
  private Boolean deleted;
  private LocalDateTime deletedAt;
}
