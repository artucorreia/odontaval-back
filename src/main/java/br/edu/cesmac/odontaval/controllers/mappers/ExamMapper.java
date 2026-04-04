package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.ExamRequestDTO;
import br.edu.cesmac.odontaval.models.ExamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mappings(
            value = {
                    @Mapping(target = "id", ignore = true),
            })
    ExamEntity examRequestDTOToEntity(ExamRequestDTO examRequestDTO);

}