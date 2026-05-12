package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardStatsResponseDTO {
  private Long totalStudents;
  private Long totalEvaluations;
  private Long evaluationsThisMonth;
  private List<RecentEvaluationResponseDTO> recentEvaluations;
}
