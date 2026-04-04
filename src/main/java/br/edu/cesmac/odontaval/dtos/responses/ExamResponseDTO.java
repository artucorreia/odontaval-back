package br.edu.cesmac.odontaval.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record ExamResponseDTO(
        Long id,
        String title,
        LocalDate date,
        String academicSemester,
        String goals,
        String serviceUnit,
        String procedurePerformed,
        UUID professorId,   // Use UUID here to match UserEntity
        Long specialismId   // Use Long here to match SpecialismEntity
) {}