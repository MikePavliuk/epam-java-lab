package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import java.math.BigDecimal;

public interface UserService extends CrudService<UserDto, Long> {

  UserDto addFunds(long id, BigDecimal amount);
}
