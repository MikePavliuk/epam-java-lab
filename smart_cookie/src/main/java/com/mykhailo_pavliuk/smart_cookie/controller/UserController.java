package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final SubscriptionService subscriptionService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable long id) {
		log.info("Get user by id {}", id);
		return userService.getUser(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping()
	public List<UserDto> getAllUsers() {
		log.info("Get all users");
		return userService.getAllUsers();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public UserDto createUser(@RequestBody UserDto userDto) {
		return userService.createUser(userDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{id}")
	public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
		log.info("Update user by id {}", id);
		log.trace("Request body userDto {}", userDto);
		return userService.updateUser(id, userDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		log.info("Delete user by id {}", id);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/{userId}/publications/{publicationId}")
	public UserDto addSubscriptionToUser(@PathVariable long userId,
										 @PathVariable long publicationId,
										 @RequestParam int periodInMonths) {
		log.info("Subscribe user with id {} to publication with id {} for {} (period in months)", userId, publicationId, periodInMonths);
		return subscriptionService.addSubscriptionToUser(userId, publicationId, periodInMonths);
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{id}/add-funds")
	public UserDto addFundsToUser(@PathVariable long id, @RequestParam BigDecimal amount) {
		log.info("Add funds to user with id {} in amount of {}", id, amount);
		return userService.addFunds(id, amount);
	}

}
