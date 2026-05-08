package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.controllers.mappers.EvaluationMapper;
import br.edu.cesmac.odontaval.dtos.responses.*;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import br.edu.cesmac.odontaval.services.DashboardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

  private final UserRepository userRepository;
  private final EvaluationRepository evaluationRepository;
  private final EvaluationMapper evaluationMapper;

  private static final String[] MONTH_PT =
      {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

  private static final List<String> CRITERIA_ORDER =
      List.of("punctuality", "instrumental", "boxOrganization", "biosecurity", "ethics", "concept");

  private static final Map<String, String> CRITERIA_LABELS = Map.of(
      "punctuality",    "Pontualidade",
      "instrumental",   "Instrumental",
      "boxOrganization","Organização do Box",
      "biosecurity",    "Biossegurança",
      "ethics",         "Ética",
      "concept",        "Conceito"
  );

  private static final String[] BUCKET_NAMES   = {"Excelente", "Bom", "Regular", "Baixo"};
  private static final String[] BUCKET_COLORS  = {"#00B894", "#6C5CE7", "#FDCB6E", "#E17055"};
  private static final double[] BUCKET_MIN     = {9, 7, 5, 0};
  private static final double[] BUCKET_MAX     = {10, 9, 7, 5};

  // ── Main dashboard stats ──────────────────────────────────────────────────

  @Override
  @Transactional
  public DashboardStatsResponseDTO getStats() {
    log.info("Fetching dashboard stats");

    long totalStudents = userRepository.countByRolesNameIgnoreCase("STUDENT");
    long totalEvaluations = evaluationRepository.countByDeletedFalse();

    LocalDate today = LocalDate.now();
    LocalDate firstDayOfMonth = today.withDayOfMonth(1);
    LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
    long evaluationsThisMonth = evaluationRepository
        .countByDateBetweenAndDeletedFalse(firstDayOfMonth, lastDayOfMonth);

    List<EvaluationEntity> recent = evaluationRepository.findTop5ByDeletedFalseOrderByCreatedAtDesc();
    List<RecentEvaluationResponseDTO> recentEvaluations = recent.stream()
        .map(evaluationMapper::evaluationEntityToRecentResponseDTO)
        .toList();

    return new DashboardStatsResponseDTO(totalStudents, totalEvaluations, evaluationsThisMonth, recentEvaluations);
  }

  // ── Semester dashboard ────────────────────────────────────────────────────

  @Override
  @Transactional
  public SemesterDashboardResponseDTO getSemesterDashboard(String semester) {
    log.info("Fetching semester dashboard for semester={}", semester);

    List<String> availableSemesters = evaluationRepository.findDistinctAcademicSemesters();

    String target = (semester == null || semester.isBlank())
        ? (availableSemesters.isEmpty() ? null : availableSemesters.get(0))
        : semester;

    List<EvaluationEntity> evals = (target != null)
        ? evaluationRepository.findByAcademicSemesterAndDeletedFalseOrderByDateAsc(target)
        : List.of();

    return new SemesterDashboardResponseDTO(
        target,
        computeKpis(evals),
        computeEvaluationsOverTime(evals),
        computeSpecialtyPerformance(evals),
        computeAverageTrend(evals),
        computeConceptDistribution(evals),
        computeCriteriaComparison(evals),
        computeHeatmap(evals),
        computeTopStudents(evals),
        availableSemesters
    );
  }

  // ── Computation helpers ───────────────────────────────────────────────────

  private static double round1(double v) {
    return Math.round(v * 10.0) / 10.0;
  }

  private SemesterKpisResponseDTO computeKpis(List<EvaluationEntity> evals) {
    long totalStudents = evals.stream().map(e -> e.getStudent().getId()).distinct().count();
    long totalProfessors = evals.stream().map(e -> e.getProfessor().getId()).distinct().count();
    double avgGrade = evals.isEmpty() ? 0.0
        : round1(evals.stream().mapToDouble(EvaluationEntity::getGrade).average().orElse(0.0));
    return new SemesterKpisResponseDTO((long) evals.size(), totalStudents, totalProfessors, avgGrade);
  }

  private List<EvaluationOverTimeDatumDTO> computeEvaluationsOverTime(List<EvaluationEntity> evals) {
    Map<Integer, long[]> byMonth = new LinkedHashMap<>();
    for (EvaluationEntity e : evals) {
      if (e.getDate() == null) continue;
      int m = e.getDate().getMonthValue() - 1;
      byMonth.computeIfAbsent(m, k -> new long[]{0, 0});
      byMonth.get(m)[0]++;
      byMonth.get(m)[1] += (long) (e.getGrade() * 10);
    }
    return byMonth.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> {
          long count = entry.getValue()[0];
          double avg = round1((entry.getValue()[1] / 10.0) / count);
          return new EvaluationOverTimeDatumDTO(MONTH_PT[entry.getKey()], count, avg);
        })
        .collect(Collectors.toList());
  }

  private List<SpecialtyPerformanceDatumDTO> computeSpecialtyPerformance(List<EvaluationEntity> evals) {
    Map<String, List<Double>> bySpecialty = new LinkedHashMap<>();
    for (EvaluationEntity e : evals) {
      String name = (e.getSpecialism() != null) ? e.getSpecialism().getName() : "Outra";
      bySpecialty.computeIfAbsent(name, k -> new ArrayList<>()).add(e.getGrade());
    }
    return bySpecialty.entrySet().stream()
        .map(entry -> {
          List<Double> grades = entry.getValue();
          double avg = round1(grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
          return new SpecialtyPerformanceDatumDTO(entry.getKey(), avg, (long) grades.size());
        })
        .sorted(Comparator.comparingDouble(SpecialtyPerformanceDatumDTO::getAvgGrade).reversed())
        .collect(Collectors.toList());
  }

  private List<AverageTrendDatumDTO> computeAverageTrend(List<EvaluationEntity> evals) {
    Map<Integer, List<Double>> byMonth = new LinkedHashMap<>();
    for (EvaluationEntity e : evals) {
      if (e.getDate() == null) continue;
      int m = e.getDate().getMonthValue() - 1;
      byMonth.computeIfAbsent(m, k -> new ArrayList<>()).add(e.getGrade());
    }
    return byMonth.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> {
          double avg = round1(entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
          return new AverageTrendDatumDTO(MONTH_PT[entry.getKey()], avg);
        })
        .collect(Collectors.toList());
  }

  private List<ConceptDistributionDatumDTO> computeConceptDistribution(List<EvaluationEntity> evals) {
    long[] counts = new long[BUCKET_NAMES.length];
    for (EvaluationEntity e : evals) {
      for (int i = 0; i < BUCKET_NAMES.length; i++) {
        if (e.getGrade() >= BUCKET_MIN[i] && e.getGrade() < BUCKET_MAX[i]) {
          counts[i]++;
          break;
        }
      }
    }
    long total = Math.max(1, evals.size());
    List<ConceptDistributionDatumDTO> result = new ArrayList<>();
    for (int i = 0; i < BUCKET_NAMES.length; i++) {
      double pct = round1((counts[i] * 100.0) / total);
      result.add(new ConceptDistributionDatumDTO(
          BUCKET_NAMES[i], counts[i], pct, BUCKET_COLORS[i], BUCKET_MIN[i], BUCKET_MAX[i]));
    }
    return result;
  }

  private List<CriteriaComparisonDatumDTO> computeCriteriaComparison(List<EvaluationEntity> evals) {
    if (evals.isEmpty()) return List.of();
    List<double[]> avgs = CRITERIA_ORDER.stream()
        .map(key -> new double[]{
            round1(10 + evals.stream().mapToDouble(e -> getCriterionValue(e, key)).average().orElse(0.0))
        })
        .collect(Collectors.toList());

    double max = avgs.stream().mapToDouble(a -> a[0]).max().orElse(0);
    double min = avgs.stream().mapToDouble(a -> a[0]).min().orElse(0);

    List<CriteriaComparisonDatumDTO> result = new ArrayList<>();
    for (int i = 0; i < CRITERIA_ORDER.size(); i++) {
      String key = CRITERIA_ORDER.get(i);
      double avg = avgs.get(i)[0];
      result.add(new CriteriaComparisonDatumDTO(CRITERIA_LABELS.get(key), avg, avg == max, avg == min));
    }
    return result;
  }

  private double getCriterionValue(EvaluationEntity e, String key) {
    return switch (key) {
      case "punctuality"    -> e.getPunctuality();
      case "instrumental"   -> e.getInstrumental();
      case "boxOrganization"-> e.getBoxOrganization();
      case "biosecurity"    -> e.getBiosecurity();
      case "ethics"         -> e.getEthics();
      case "concept"        -> e.getConcept();
      default               -> 0;
    };
  }

  private List<HeatmapCellDTO> computeHeatmap(List<EvaluationEntity> evals) {
    Optional<LocalDate> firstOpt = evals.stream()
        .map(EvaluationEntity::getDate)
        .filter(Objects::nonNull)
        .min(Comparator.naturalOrder());
    if (firstOpt.isEmpty()) return List.of();

    LocalDate firstDate = firstOpt.get();
    DayOfWeek dow = firstDate.getDayOfWeek();
    int adjustment = (dow == DayOfWeek.SUNDAY) ? 6 : dow.getValue() - 1;
    LocalDate semStart = firstDate.minusDays(adjustment);

    Map<String, Long> cells = new HashMap<>();
    for (EvaluationEntity e : evals) {
      if (e.getDate() == null) continue;
      LocalDate date = e.getDate();
      DayOfWeek dayOfWeek = date.getDayOfWeek();
      int mappedDow = (dayOfWeek == DayOfWeek.SATURDAY) ? 4
          : (dayOfWeek == DayOfWeek.SUNDAY) ? 0
          : dayOfWeek.getValue() - 1;
      long diffDays = ChronoUnit.DAYS.between(semStart, date);
      int week = (int) Math.max(1, diffDays / 7 + 1);
      String key = week + "-" + mappedDow;
      cells.merge(key, 1L, Long::sum);
    }

    return cells.entrySet().stream()
        .map(entry -> {
          String[] parts = entry.getKey().split("-");
          return new HeatmapCellDTO(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), entry.getValue());
        })
        .collect(Collectors.toList());
  }

  private List<TopStudentDatumDTO> computeTopStudents(List<EvaluationEntity> evals) {
    Map<UUID, List<EvaluationEntity>> byStudent = evals.stream()
        .collect(Collectors.groupingBy(e -> e.getStudent().getId()));

    return byStudent.entrySet().stream()
        .map(entry -> {
          List<EvaluationEntity> studentEvals = entry.getValue();
          String name = studentEvals.get(0).getStudent().getName();
          double avgGrade = round1(studentEvals.stream().mapToDouble(EvaluationEntity::getGrade).average().orElse(0.0));
          return new TopStudentDatumDTO(entry.getKey().toString(), name, avgGrade, (long) studentEvals.size());
        })
        .sorted(Comparator.comparingDouble(TopStudentDatumDTO::getAvgGrade).reversed())
        .limit(8)
        .collect(Collectors.toList());
  }
}
