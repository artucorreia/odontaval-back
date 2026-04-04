package br.edu.cesmac.odontaval.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

  @Column(nullable = false)
  private Double punctuality;

  @Column(nullable = false)
  private Double instrumental;

  @Column(name = "organization_of_service_unit", nullable = false)
  private Double organizationOfServiceUnit;

  @Column(nullable = false)
  private Double biosecurity;

  @Column(nullable = false)
  private Double ethics;

  @Column(nullable = false)
  private Double concept;

  @Column(length = 500)
  private String observations;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
  private UserEntity student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exam_id", referencedColumnName = "id", nullable = false)
  private ExamEntity exam;
}
