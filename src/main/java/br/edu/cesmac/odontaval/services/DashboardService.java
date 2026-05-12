package br.edu.cesmac.odontaval.services;

import br.edu.cesmac.odontaval.dtos.responses.ClassAveragesResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.SemesterDashboardResponseDTO;

import java.util.UUID;

public interface DashboardService {
  DashboardStatsResponseDTO getStats();
  SemesterDashboardResponseDTO getSemesterDashboard(String semester);
  ClassAveragesResponseDTO getClassAverages(UUID excludeStudentId);
}
