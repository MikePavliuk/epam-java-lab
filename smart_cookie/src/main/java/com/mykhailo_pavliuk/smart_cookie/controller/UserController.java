package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SubscriptionService subscriptionService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public UserDto getById(@PathVariable long id) {
    log.info("Get user by id {}", id);
    return userService.getById(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/search")
  public UserDto getByEmail(@RequestParam String email) {
    log.info("Get user by email {}", email);
    return userService.getByEmail(email);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public List<UserDto> getAll() {
    log.info("Get all users");
    return userService.getAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping()
  public UserDto create(@RequestBody UserDto userDto) {
    log.info("Create user {}", userDto);
    return userService.create(userDto);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}")
  public UserDto updateById(@PathVariable long id, @RequestBody UserDto userDto) {
    log.info("Update user by id {} with request body {}", id, userDto);
    return userService.updateById(id, userDto);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable long id) {
    log.info("Delete user by id {}", id);
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{userId}/publications/{publicationId}")
  public UserDto addSubscriptionToUser(
      @PathVariable long userId,
      @PathVariable long publicationId,
      @RequestParam int periodInMonths) {
    log.info(
        "Subscribe user with id '{}' to publication with id '{}' for {} month(s)",
        userId,
        publicationId,
        periodInMonths);
    return subscriptionService.addSubscriptionToUser(userId, publicationId, periodInMonths);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}/add-funds")
  public UserDto addFundsToUser(@PathVariable long id, @RequestParam BigDecimal amount) {
    log.info("Add funds to user with id {} in amount of {}", id, amount);
    return userService.addFunds(id, amount);
  }
}
