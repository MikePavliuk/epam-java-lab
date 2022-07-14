package com.mykhailo_pavliuk.smart_cookie.model;

import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

  private String message;
  private ErrorType errorType;
  private LocalDateTime timeStamp;
}
