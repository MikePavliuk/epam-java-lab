package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.api.UserApi;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
  public Page<UserDto> getAllUsers(Pageable pageable) {
    log.info("Get all users");
    return userService.getAllUsers(pageable);
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    return userService.createUser(userDto);
  }

  @Override
  public UserDto updateUser(long id, UserDto userDto) {
    log.info("Update user by id {}", id);
    return userService.updateUser(id, userDto);
  }

  @Override
  public ResponseEntity<Void> deleteUser(long id) {
    log.info("Delete user by id {}", id);
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> addSubscriptionToUser(
      long userId, long publicationId, int periodInMonths) {
    log.info("Subscribe user to publication");
    subscriptionService.addSubscriptionToUser(userId, publicationId, periodInMonths);
    return ResponseEntity.noContent().build();
  }

  @Override
  public UserDto addFundsToUser(long id, BigDecimal amount) {
    log.info("Add funds to user with id {} in amount of {}", id, amount);
    return userService.addFunds(id, amount);
  }
}
