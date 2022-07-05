package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {

  UserDto getUser(long id);

  List<UserDto> getAllUsers();

  UserDto createUser(UserDto user);

  UserDto updateUser(long id, UserDto user);

  void deleteUser(long id);

  UserDto addFunds(long id, BigDecimal amount);

  boolean existsByEmail(String email);
}
