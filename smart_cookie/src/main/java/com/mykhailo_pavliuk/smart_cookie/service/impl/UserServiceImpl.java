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
import java.util.Optional;
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
  public UserDto getById(Long id) {
    log.info("Started getting user by id");
    Optional<User> user = userRepository.findById(id);
    log.info("Finished getting user by id ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user.orElseThrow(EntityNotFoundException::new));
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

    userDto.setRole(Role.builder().id(1L).name(RoleDto.SUBSCRIBER.name().toLowerCase()).build());

    userDto.setStatus(
        UserStatus.builder().id(1L).name(UserStatusDto.ACTIVE.name().toLowerCase()).build());

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
      throw new EntityNotFoundException("User with id " + id + " is not found");
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
    Optional<User> user = userRepository.findByEmail(email);
    log.info("Finished getting user by email ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user.orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public UserDto addFunds(long id, BigDecimal amount) {
    log.info("Started adding funds");
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new EntityNotFoundException();
    }

    if (user.get().getUserDetail() == null) {
      throw new EntityIllegalArgumentException("User is not allowed to have balance");
    }

    user.get().getUserDetail().setBalance(user.get().getUserDetail().getBalance().add(amount));
    User updatedUser = userRepository.save(user.get());

    log.info("Finished adding funds");

    return UserMapper.INSTANCE.mapUserToUserDto(updatedUser);
  }

  @Override
  public boolean existsByEmail(String email) {
    log.info("Checking if exists by email");
    return userRepository.existsByEmail(email);
  }
}
