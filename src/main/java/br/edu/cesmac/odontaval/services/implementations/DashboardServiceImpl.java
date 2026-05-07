package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.RecentExamResponseDTO;
import br.edu.cesmac.odontaval.models.ExamEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.repositories.ExamRepository;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import br.edu.cesmac.odontaval.services.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

  private final UserRepository userRepository;
  private final EvaluationRepository evaluationRepository;
  private final ExamRepository examRepository;

  @Override
  public DashboardStatsResponseDTO getStats() {
    log.info("Fetching dashboard stats");

    long totalStudents = userRepository.countByRolesNameIgnoreCase("STUDENT");
    long totalEvaluations = evaluationRepository.countByDeletedFalse();
    long todayExams = examRepository.countByDateAndDeletedFalse(LocalDate.now());

    List<ExamEntity> recent = examRepository.findTop5ByDeletedFalseOrderByCreatedAtDesc();
    List<RecentExamResponseDTO> recentExams =
        recent.stream()
            .map(
                e ->
                    new RecentExamResponseDTO(
                        e.getId(),
                        e.getTitle(),
                        e.getSpecialism().getName(),
                        e.getAcademicSemester(),
                        e.getDate()))
            .toList();

    return new DashboardStatsResponseDTO(totalStudents, totalEvaluations, todayExams, recentExams);
  }
}
