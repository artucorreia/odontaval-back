package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.EvaluationRequestDTO;
import br.edu.cesmac.odontaval.models.EvaluationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "studentId", target = "student.id"),
            @Mapping(source = "examId", target = "exam.id")
    })
    EvaluationEntity evaluationRequestDTOToEntity(EvaluationRequestDTO evaluationRequestDTO);
}