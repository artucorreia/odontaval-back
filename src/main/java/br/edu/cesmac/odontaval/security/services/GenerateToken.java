package br.edu.cesmac.odontaval.security.services;

import java.util.UUID;

public interface GenerateToken {
  String generate(UUID userId);
}
