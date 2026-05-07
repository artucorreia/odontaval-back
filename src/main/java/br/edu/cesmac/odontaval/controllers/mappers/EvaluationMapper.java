package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.EvaluationInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.EvaluationUpdateRequestDTO;
import br.edu.cesmac.odontaval.dtos.responses.EvaluationResponseDTO;
import br.edu.cesmac.odontaval.dtos.responses.RecentEvaluationResponseDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "professor", ignore = true),
    @Mapping(target = "grade", ignore = true),
    @Mapping(source = "studentId", target = "student.id"),
    @Mapping(source = "specialismId", target = "specialism.id")
  })
  EvaluationEntity evaluationInsertRequestDTOToEntity(EvaluationInsertRequestDTO dto);

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "professor", ignore = true),
    @Mapping(target = "student", ignore = true),
    @Mapping(target = "specialism", ignore = true),
    @Mapping(target = "grade", ignore = true)
  })
  EvaluationEntity evaluationUpdateRequestDTOToEntity(EvaluationUpdateRequestDTO dto);

  @Mappings({
    @Mapping(source = "professor.id", target = "professorId"),
    @Mapping(source = "student.id", target = "studentId"),
    @Mapping(source = "specialism.id", target = "specialismId")
  })
  EvaluationResponseDTO evaluationEntityToResponseDTO(EvaluationEntity entity);

  List<EvaluationResponseDTO> evaluationEntitiesToResponseDTOs(List<EvaluationEntity> entities);

  @Mapping(source = "specialism.name", target = "specialismName")
  RecentEvaluationResponseDTO evaluationEntityToRecentResponseDTO(EvaluationEntity entity);
}
