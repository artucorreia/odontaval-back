package br.edu.cesmac.odontaval.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "exams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ExamEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "academic_semester", length = 2)
  private String academicSemester;

  @Column(length = 500)
  private String goals;

  @Column(name = "service_unit", length = 100)
  private String serviceUnit;

  @Column(name = "procedure_performed", length = 50)
  private String procedurePerformed;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
  private UserEntity professor;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "specialism_id", referencedColumnName = "id", nullable = false)
  private SpecialismEntity specialism;
}
