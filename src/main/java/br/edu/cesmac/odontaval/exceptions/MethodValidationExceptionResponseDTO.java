package br.edu.cesmac.odontaval.exceptions;

import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class MethodValidationExceptionResponseDTO extends ExceptionResponseDTO {
  private Map<String, String> fields;
}
