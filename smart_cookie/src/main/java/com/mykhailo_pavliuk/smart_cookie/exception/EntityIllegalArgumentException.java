package com.mykhailo_pavliuk.smart_cookie.exception;

import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;

public class EntityIllegalArgumentException extends ServiceException {
  private static final String DEFAULT_MESSAGE = "Entity can't be performed in operation";

  public EntityIllegalArgumentException() {
    super(DEFAULT_MESSAGE);
  }

  public EntityIllegalArgumentException(String message) {
    super(message);
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.PROCESSING_ERROR_TYPE;
  }
}
