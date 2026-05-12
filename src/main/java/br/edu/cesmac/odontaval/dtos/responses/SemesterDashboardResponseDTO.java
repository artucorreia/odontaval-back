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
public class SemesterDashboardResponseDTO {
  private String semester;
  private SemesterKpisResponseDTO kpis;
  private List<EvaluationOverTimeDatumDTO> evaluationsOverTime;
  private List<SpecialtyPerformanceDatumDTO> specialtyPerformance;
  private List<AverageTrendDatumDTO> averageTrend;
  private List<ConceptDistributionDatumDTO> conceptDistribution;
  private List<CriteriaComparisonDatumDTO> criteriaComparison;
  private List<HeatmapCellDTO> heatmap;
  private List<TopStudentDatumDTO> topStudents;
  private List<String> availableSemesters;
}
