package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UserDto getUser(long id);

  Page<UserDto> getAllUsers(Pageable pageable);

  UserDto createUser(UserDto user);

  UserDto updateUser(long id, UserDto user);

  void deleteUser(long id);

  UserDto addFunds(long id, BigDecimal amount);

  boolean existsByEmail(String email);
}
