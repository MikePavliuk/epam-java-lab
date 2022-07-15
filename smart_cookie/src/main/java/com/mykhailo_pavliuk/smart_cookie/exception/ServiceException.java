package com.mykhailo_pavliuk.smart_cookie.exception;

import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ServiceException extends RuntimeException {

  protected ServiceException(String message) {
    super(message);
  }

  public ErrorType getErrorType() {
    return ErrorType.FATAL_ERROR_TYPE;
  }
}
