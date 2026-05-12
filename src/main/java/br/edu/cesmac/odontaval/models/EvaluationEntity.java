package br.edu.cesmac.odontaval.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "evaluations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class EvaluationEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String title;

  @Column(nullable = false)
  private Double punctuality;

  @Column(nullable = false)
  private Double instrumental;

  @Column(name = "box_organization", nullable = false)
  private Double boxOrganization;

  @Column(nullable = false)
  private Double biosecurity;

  @Column(nullable = false)
  private Double ethics;

  @Column(nullable = false)
  private Double concept;

  @Column(nullable = false)
  private Double grade;

  @Column(columnDefinition = "TEXT")
  private String observations;

  @Column(name = "evaluation_number", length = 3)
  private String evaluationNumber;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "academic_semester", length = 6)
  private String academicSemester;

  @Column(columnDefinition = "TEXT")
  private String goals;

  @Column(length = 20)
  private String box;

  @Column(name = "procedure_performed", length = 50)
  private String procedurePerformed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
  private UserEntity professor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
  private UserEntity student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "specialism_id", referencedColumnName = "id", nullable = false)
  private SpecialismEntity specialism;
}
