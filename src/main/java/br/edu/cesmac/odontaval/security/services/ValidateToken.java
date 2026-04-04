package br.edu.cesmac.odontaval.security.services;

import java.util.UUID;

public interface ValidateToken {
  UUID validate(String token);
}
