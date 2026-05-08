package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.SemesterDashboardResponseDTO;

public interface DashboardService {
  DashboardStatsResponseDTO getStats();
  SemesterDashboardResponseDTO getSemesterDashboard(String semester);
}
