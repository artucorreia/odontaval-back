package br.edu.cesmac.odontaval.controllers.mappers;

import br.edu.cesmac.odontaval.dtos.requests.ExamInsertRequestDTO;
import br.edu.cesmac.odontaval.dtos.requests.ExamUpdateRequestDTO;
import br.edu.cesmac.odontaval.models.ExamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ExamMapper {

  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "professor", ignore = true),
        @Mapping(source = "specialismId", target = "specialism.id")
      })
  ExamEntity examInsertRequestDTOToEntity(ExamInsertRequestDTO examInsertRequestDTO);


  @Mappings(
      value = {
          @Mapping(target = "id", ignore = true),
          @Mapping(target = "professor", ignore = true),
          @Mapping(source = "specialismId", target = "specialism.id")
      })
  ExamEntity examUpdateRequestDTOToEntity(ExamUpdateRequestDTO examUpdateRequestDTO);
}
