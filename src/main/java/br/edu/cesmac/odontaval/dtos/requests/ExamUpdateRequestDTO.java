package br.edu.cesmac.odontaval.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamUpdateRequestDTO {

  @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
  private String title;

  @FutureOrPresent(message = "A data deve ser igual ou superior ao dia de hoje")
  private LocalDate date;

  @Min(value = 1, message = "O id da especialidade deve ser maior que 0")
  private Long specialismId;

  @Size(min = 1, max = 2, message = "O semestre acadêmico deve ter entre 1 e 2 caracteres")
  private String academicSemester;

  @Size(min = 10, max = 500, message = "Os objetivos devem ter entre 10 e 500 caracteres")
  private String goals;

  @Size(min = 5, max = 100, message = "A unidade de serviço deve ter entre 5 e 100 caracteres")
  private String serviceUnit;

  @Size(min = 5, max = 50, message = "O procedimento realizado deve ter entre 5 e 50 caracteres")
  private String procedurePerformed;
}