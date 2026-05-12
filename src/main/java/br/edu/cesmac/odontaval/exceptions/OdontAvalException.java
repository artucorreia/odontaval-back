package br.edu.cesmac.odontaval.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus
public class OdontAvalException extends RuntimeException {
  private HttpStatus status;

  public OdontAvalException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
