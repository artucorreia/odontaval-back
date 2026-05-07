package br.edu.cesmac.odontaval.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TokenResponseDTO {
  private UUID userId;
  private String userRole;
  private String token;
}
