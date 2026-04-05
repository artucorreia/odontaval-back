package br.edu.cesmac.odontaval.constant;

import org.springframework.http.HttpStatus;

public class ExamConstant {
  private ExamConstant() {}

  public static final String MESSAGE_200 = "Ação realizada com sucesso";
  public static final Integer STATUS_200 = HttpStatus.OK.value();

  public static final String UPDATE_MESSAGE = "Exame atualizado com sucesso";
  public static final Integer UPDATE_STATUS = HttpStatus.OK.value();

  public static final String INSERT_MESSAGE = "Exame criado com sucesso";
  public static final Integer INSERT_STATUS = HttpStatus.CREATED.value();
}
