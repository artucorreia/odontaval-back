package br.edu.cesmac.odontaval.dtos.responses;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
  private UUID id;
  private String name;
  private String email;
  private Set<RoleResponseDTO> roles;
  private LocalDateTime createdAt;
}
