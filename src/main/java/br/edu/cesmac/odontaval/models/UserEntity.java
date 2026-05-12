package br.edu.cesmac.odontaval.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "verified_email", nullable = false)
  private Boolean verifiedEmail;

  @Column(name = "verified_email_at")
  private LocalDateTime verifiedEmailAt;

  @Column(name = "verified_email_by")
  private UUID verifiedEmailBy;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
  private Set<RoleEntity> roles = new HashSet<>();
}
