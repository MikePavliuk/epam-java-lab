package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{email}")
	public UserDto getUser(@PathVariable String email) {
		log.info("Get user by email {}", email);
		return userService.getUser(email);
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
	@PutMapping(value = "/{email}")
	public UserDto updateUser(@PathVariable String email, @RequestBody UserDto userDto) {
		log.info("Update user by email {}", email);
		log.trace("Request body userDt0 {}", userDto);
		return userService.updateUser(email, userDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable String email) {
		log.info("Delete user by email {}", email);
		userService.deleteUser(email);
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{email}/subscribe")
	public UserDto addSubscriptionToUser(@PathVariable String email, @RequestBody Subscription subscription) {
		log.info("Subscribe user with email {} to publication with id {}", email, subscription.getPublicationId());
		return userService.addSubscriptionToUser(email, subscription);
	}


}
