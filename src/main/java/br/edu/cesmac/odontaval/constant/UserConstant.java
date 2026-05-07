package br.edu.cesmac.odontaval.constant;

import org.springframework.http.HttpStatus;

public class UserConstant {
  private UserConstant() {}

  public static final String MESSAGE_200 = "Ação realizada com sucesso";
  public static final Integer STATUS_200 = HttpStatus.OK.value();
}
