package br.edu.cesmac.odontaval.exceptions;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ExceptionResponseDTO {
  private Boolean success;
  private String message;
  private Integer code;
  private String uri;
  private LocalDateTime timestamp;
}
