package br.edu.cesmac.odontaval.dtos.requests;

import br.edu.cesmac.odontaval.models.SpecialismEntity;
import br.edu.cesmac.odontaval.models.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

public class ExamRequestDTO {
    private String title;
    private LocalDate date;
    private UserEntity professor;
    private SpecialismEntity specialism;
    private String academicSemester;
    private String goals;
    private String serviceUnit;
    private String procedurePerformed;

}
