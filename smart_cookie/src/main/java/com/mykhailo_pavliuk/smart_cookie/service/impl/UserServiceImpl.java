package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.RoleDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserStatusDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserDetailMapper;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.model.UserDetail;
import com.mykhailo_pavliuk.smart_cookie.repository.RoleRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserStatusRepository;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import java.math.BigDecimal;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserStatusRepository userStatusRepository;

  @Override
  public UserDto getById(Long id) {
    log.info("Started getting user by id");
    Optional<User> user = userRepository.findById(id);
    log.info("Finished getting user by id ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user.orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public Page<UserDto> getAll(Pageable pageable) {
    log.info("Getting all users");
    return userRepository.findAll(pageable).map(UserMapper.INSTANCE::mapUserToUserDto);
  }

  @Transactional
  @Override
  public UserDto create(UserDto userDto) {
    log.info("Started creating user");

    userDto.setRole(
        roleRepository
            .findByName(RoleDto.SUBSCRIBER.name().toLowerCase())
            .orElseThrow(EntityNotFoundException::new));

    userDto.setStatus(
        userStatusRepository
            .findByName(UserStatusDto.ACTIVE.name().toLowerCase())
            .orElseThrow(EntityNotFoundException::new));

    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

    UserDetail userDetail =
        UserDetailMapper.INSTANCE.mapUserDetailDtoToUserDetail(userDto.getUserDetail());
    userDetail.setBalance(BigDecimal.ZERO);

    user.setUserDetail(userDetail);
    userDetail.setUser(user);

    user = userRepository.save(user);

    log.info("Finished creating user ({})", user);

    return UserMapper.INSTANCE.mapUserToUserDto(user);
  }

  @Transactional
  @Override
  public UserDto updateById(Long id, UserDto userDto) {
    log.info("Started updating user by id");

    if (!userRepository.existsById(id)) {
      throw new EntityNotFoundException("User with id " + id + " is not found");
    }

    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
    user.getUserDetail().setUser(user);
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

  @Transactional
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
