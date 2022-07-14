package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.RoleDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserStatusDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserDetailMapper;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
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

  private static final String DEFAULT_CREATED_USER_ROLE_NAME =
      RoleDto.SUBSCRIBER.name().toLowerCase();
  private static final String DEFAULT_CREATED_USER_STATUS_NAME =
      UserStatusDto.ACTIVE.name().toLowerCase();

  private final UserRepository userRepository;

  @Override
  public UserDto getById(Long id) {
    log.info("Started getting user by id");
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getMessageUserIsNotFoundById(id)));
    log.info("Finished getting user by id ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public List<UserDto> getAll() {
    log.info("Getting all users");
    return userRepository.getAll().stream()
        .map(UserMapper.INSTANCE::mapUserToUserDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto create(UserDto userDto) {
    log.info("Started creating user");

    userDto.setRole(Role.builder().id(1L).name(DEFAULT_CREATED_USER_ROLE_NAME).build());

    userDto.setStatus(UserStatus.builder().id(1L).name(DEFAULT_CREATED_USER_STATUS_NAME).build());

    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

    user.setUserDetail(
        UserDetailMapper.INSTANCE.mapUserDetailDtoToUserDetail(userDto.getUserDetail()));
    user.getUserDetail().setBalance(BigDecimal.ZERO);

    user = userRepository.save(user);

    log.info("Finished creating user ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public UserDto updateById(Long id, UserDto userDto) {
    log.info("Started updating user by id");

    if (!userRepository.existsById(id)) {
      throw new EntityNotFoundException(getMessageUserIsNotFoundById(id));
    }

    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
    user.setUserDetail(
        UserDetailMapper.INSTANCE.mapUserDetailDtoToUserDetail(userDto.getUserDetail()));
    user = userRepository.save(user);

    log.info("Finished updating user by id ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public void deleteById(Long id) {
    log.info("Started deleting user by id");
    userRepository.deleteById(id);
    log.info("Finished deleting user by id");
  }

  @Override
  public UserDto getByEmail(String email) {
    log.info("Started getting user by email");
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("User with email '%s' is not found!", email)));
    log.info("Finished getting user by email ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Override
  public UserDto addFunds(long id, BigDecimal amount) {
    log.info("Started adding funds");
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getMessageUserIsNotFoundById(id)));

    if (user.getUserDetail() == null) {
      throw new EntityIllegalArgumentException(
          String.format(
              "User is not allowed to have balance! User's role is '%s'.",
              user.getRole().getName()));
    }

    user.getUserDetail().setBalance(user.getUserDetail().getBalance().add(amount));
    User updatedUser = userRepository.save(user);

    log.info("Finished adding funds");

    return UserMapper.INSTANCE.mapUserToUserDto(updatedUser);
  }

  private String getMessageUserIsNotFoundById(long id) {
    return String.format("User with id '%d' is not found!", id);
  }
}
