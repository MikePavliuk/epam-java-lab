package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.api.UserApi;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserService userService;
	private final SubscriptionService subscriptionService;

	@Override
	public UserDto getUser(long id) {
		log.info("Get user by id {}", id);
		return userService.getUser(id);
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Get all users");
		return userService.getAllUsers();
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		return userService.createUser(userDto);
	}

	@Override
	public UserDto updateUser(long id, UserDto userDto) {
		log.info("Update user by id {}", id);
		log.trace("Request body userDto {}", userDto);
		return userService.updateUser(id, userDto);
	}

	@Override
	public ResponseEntity<Void> deleteUser(long id) {
		log.info("Delete user by id {}", id);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public UserDto addSubscriptionToUser(long userId, long publicationId, int periodInMonths) {
		log.info("Subscribe user with id {} to publication with id {} for {} (period in months)", userId, publicationId, periodInMonths);
		return subscriptionService.addSubscriptionToUser(userId, publicationId, periodInMonths);
	}

	@Override
	public UserDto addFundsToUser(long id, BigDecimal amount) {
		log.info("Add funds to user with id {} in amount of {}", id, amount);
		return userService.addFunds(id, amount);
	}

}
