package br.edu.cesmac.odontaval.constant;

import org.springframework.http.HttpStatus;

public class AuthConstant {
  private AuthConstant() {}

  public static final String MESSAGE_200 = "Ação realizada com sucesso";
  public static final Integer STATUS_200 = HttpStatus.OK.value();

  public static final String REGISTER_MESSAGE = "Registro concluído com sucesso";
  public static final Integer REGISTER_STATUS = HttpStatus.OK.value();
}
