package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

	UserDto getUser(String email);

	List<UserDto> getAllUsers();

	UserDto createUser(UserDto user);

	UserDto updateUser(String email, UserDto user);

	void deleteUser(String email);

	UserDto addSubscriptionToUser(String userEmail, Subscription subscription);

	UserDto addFunds(String userEmail, BigDecimal amount);

}
