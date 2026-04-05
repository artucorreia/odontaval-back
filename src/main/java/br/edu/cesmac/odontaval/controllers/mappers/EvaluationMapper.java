package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.EvaluationInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.EvaluationUpdateRequestDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "student", ignore = true),
    @Mapping(target = "exam", ignore = true),
  })
  EvaluationEntity evaluationUpdateRequestDTOToEntity(
      EvaluationUpdateRequestDTO evaluationUpdateRequestDTO);

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(source = "studentId", target = "student.id"),
    @Mapping(source = "examId", target = "exam.id")
  })
  EvaluationEntity evaluationInsertRequestDTOToEntity(
      EvaluationInsertRequestDTO evaluationInsertRequestDTO);
}
