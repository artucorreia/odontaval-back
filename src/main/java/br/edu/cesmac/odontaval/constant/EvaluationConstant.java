package br.edu.cesmac.odontaval.constant;

import org.springframework.http.HttpStatus;

public class EvaluationConstant {
  private EvaluationConstant() {}

  public static final String MESSAGE_200 = "Ação realizada com sucesso";
  public static final Integer STATUS_200 = HttpStatus.OK.value();

  public static final String UPDATE_MESSAGE = "Avaliação atualizada com sucesso";
  public static final Integer UPDATE_STATUS = HttpStatus.OK.value();

  public static final String INSERT_MESSAGE = "Avaliação criada com sucesso";
  public static final Integer INSERT_STATUS = HttpStatus.CREATED.value();
}
