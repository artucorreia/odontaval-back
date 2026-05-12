package br.edu.cesmac.odontaval.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final String METHOD_ARGUMENT_NOT_VALID_MESSAGE = "invalid fields";
  private final String METHOD_NOT_ALLOWED_MESSAGE = "invalid request method '%s'";
  private final String NO_RESOURCE_FOUND_MESSAGE = "route not found";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError fieldError : ex.getFieldErrors()) {
      fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    ExceptionResponseDTO response =
        MethodValidationExceptionResponseDTO.builder()
            .success(false)
            .message(METHOD_ARGUMENT_NOT_VALID_MESSAGE)
            .code(HttpStatus.BAD_REQUEST.value())
            .fields(fieldErrors)
            .uri(request.getDescription(false))
            .timestamp(LocalDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ExceptionResponseDTO response =
        ExceptionResponseDTO.builder()
            .success(false)
            .message(String.format(METHOD_NOT_ALLOWED_MESSAGE, ex.getMethod()))
            .code(HttpStatus.METHOD_NOT_ALLOWED.value())
            .uri(request.getDescription(false))
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleNoResourceFoundException(
      NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ExceptionResponseDTO response =
        ExceptionResponseDTO.builder()
            .success(false)
            .message(NO_RESOURCE_FOUND_MESSAGE)
            .code(HttpStatus.NOT_FOUND.value())
            .uri(request.getDescription(false))
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseDTO> handlerAllExceptions(
      RuntimeException ex, WebRequest request) {
    ExceptionResponseDTO response =
        ExceptionResponseDTO.builder()
            .success(false)
            .message(ex.getMessage())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .uri(request.getDescription(false))
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(OdontAvalException.class)
  public ResponseEntity<ExceptionResponseDTO> handlerOdontAvalExceptions(
      OdontAvalException ex, WebRequest request) {
    ExceptionResponseDTO response =
        ExceptionResponseDTO.builder()
            .success(false)
            .message(ex.getMessage())
            .code(ex.getStatus().value())
            .uri(request.getDescription(false))
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(ex.getStatus()).body(response);
  }
}
