package br.edu.cesmac.odontaval.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BaseEntity {
  @Column(name = "created_by", updatable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_by", insertable = false)
  private UUID updatedBy;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_by", insertable = false)
  private UUID deletedBy;

  @Column(name = "deleted_at", insertable = false)
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  private Boolean deleted;
}
