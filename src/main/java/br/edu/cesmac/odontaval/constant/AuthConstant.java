package br.edu.cesmac.odontaval.constant;

import org.springframework.http.HttpStatus;

public class AuthConstant {
  private AuthConstant() {}

  public static final String MESSAGE_200 = "Ação realizada com sucesso";
  public static final Integer STATUS_200 = HttpStatus.OK.value();

  public static final String REGISTER_MESSAGE = "Registro concluído com sucesso";
  public static final Integer REGISTER_STATUS = HttpStatus.OK.value();

  public static final String PASSWORD_RECOVERY_MESSAGE = "E-mail de recuperação de senha enviado com sucesso";
  public static final Integer PASSWORD_RECOVERY_STATUS = HttpStatus.OK.value();

  public static final String RESET_PASSWORD_MESSAGE = "Senha alterada com sucesso";
  public static final Integer RESET_PASSWORD_STATUS = HttpStatus.OK.value();

  public static final String CONFIRM_EMAIL_MESSAGE = "E-mail confirmado com sucesso";
  public static final Integer CONFIRM_EMAIL_STATUS = HttpStatus.OK.value();
}
