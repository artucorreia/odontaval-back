package br.edu.cesmac.odontaval.dtos.requests;

import java.util.UUID;

public class EvaluationRequestDTO{
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