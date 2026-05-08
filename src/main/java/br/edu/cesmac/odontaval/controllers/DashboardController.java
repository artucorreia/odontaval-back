package br.edu.cesmac.odontaval.controllers;

import br.edu.cesmac.odontaval.constant.DashboardConstant;
import br.edu.cesmac.odontaval.dtos.ResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.DashboardStatsResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.SemesterDashboardResponseDTO;
import br.edu.cesmac.odontaval.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<DashboardStatsResponseDTO>> getStats() {
    DashboardStatsResponseDTO stats = dashboardService.getStats();
    return ResponseEntity.ok(new ResponseDTO<>(true, null, DashboardConstant.STATUS_200, stats));
  }

  @GetMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<SemesterDashboardResponseDTO>> getSemesterDashboard(
      @RequestParam(required = false) String semester) {
    SemesterDashboardResponseDTO data = dashboardService.getSemesterDashboard(semester);
    return ResponseEntity.ok(new ResponseDTO<>(true, null, DashboardConstant.STATUS_200, data));
  }
}
