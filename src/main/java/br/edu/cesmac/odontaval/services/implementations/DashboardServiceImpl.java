package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.controllers.mappers.EvaluationMapper;
import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.RecentEvaluationResponseDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
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
  private final EvaluationMapper evaluationMapper;

  @Override
  public DashboardStatsResponseDTO getStats() {
    log.info("Fetching dashboard stats");

    long totalStudents = userRepository.countByRolesNameIgnoreCase("STUDENT");
    long totalEvaluations = evaluationRepository.countByDeletedFalse();
    long todayEvaluations = evaluationRepository.countByDateAndDeletedFalse(LocalDate.now());

    List<EvaluationEntity> recent = evaluationRepository.findTop5ByDeletedFalseOrderByCreatedAtDesc();
    List<RecentEvaluationResponseDTO> recentEvaluations =
        recent.stream()
            .map(evaluationMapper::evaluationEntityToRecentResponseDTO)
            .toList();

    return new DashboardStatsResponseDTO(totalStudents, totalEvaluations, todayEvaluations, recentEvaluations);
  }
}
