package br.edu.cesmac.odontaval.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResponseDTO{
        Long id;
        Double punctuality;
        Double instrumental;
        Double organizationOfServiceUnit;
        Double biosecurity;
        Double ethics;
        Double concept;
        String observations;
        UUID studentId;
        Long examId;
}