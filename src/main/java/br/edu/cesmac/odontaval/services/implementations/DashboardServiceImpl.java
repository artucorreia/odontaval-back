package br.edu.cesmac.odontaval.services.implementations;

import br.edu.cesmac.odontaval.controllers.mappers.EvaluationMapper;
import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.RecentEvaluationResponseDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import br.edu.cesmac.odontaval.repositories.EvaluationRepository;
import br.edu.cesmac.odontaval.repositories.UserRepository;
import br.edu.cesmac.odontaval.services.DashboardService;
import jakarta.transaction.Transactional;
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
}
