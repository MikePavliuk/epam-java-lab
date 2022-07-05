package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserDetailMapper;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.model.enums.Role;
import com.mykhailo_pavliuk.smart_cookie.model.enums.Status;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDto getUser(long id) {
    log.info("Get user by id {}", id);
    User user = userRepository.getUser(id);
    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public List<UserDto> getAllUsers() {
    log.info("Get all users");
    return userRepository.getUsers().stream()
        .map(UserMapper.INSTANCE::mapUserToUserDto)
        .collect(Collectors.toList());
  }

  public UserDto createUser(UserDto userDto) {
    log.info("Create user with email {}", userDto.getEmail());
    userDto.setRole(Role.SUBSCRIBER);
    userDto.setStatus(Status.ACTIVE);
    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
    user.setUserDetail(
        UserDetailMapper.INSTANCE.mapUserDetailDtoToUserDetail(userDto.getUserDetail()));
    user.getUserDetail().setBalance(BigDecimal.ZERO);
    user = userRepository.createUser(user);
    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public UserDto updateUser(long id, UserDto userDto) {
    log.info("Update user with id {}", id);
    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
    user.setUserDetail(
        UserDetailMapper.INSTANCE.mapUserDetailDtoToUserDetail(userDto.getUserDetail()));
    user = userRepository.updateUser(id, user);
    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public void deleteUser(long id) {
    log.info("Delete user with id {}", id);
    userRepository.deleteUser(id);
  }

  @Override
  public UserDto addFunds(long id, BigDecimal amount) {
    log.info("Add funds");
    User user = userRepository.getUser(id);
    user.getUserDetail().setBalance(user.getUserDetail().getBalance().add(amount));
    User updatedUser = userRepository.updateUser(id, user);
    return UserMapper.INSTANCE.mapUserToUserDto(updatedUser);
  }
}
