package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;

public interface DashboardService {
  DashboardStatsResponseDTO getStats();
}
