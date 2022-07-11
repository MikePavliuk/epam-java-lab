package com.mykhailo_pavliuk.smart_cookie.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
import com.mykhailo_pavliuk.smart_cookie.repository.RoleRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserStatusRepository;
import com.mykhailo_pavliuk.smart_cookie.service.impl.UserServiceImpl;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

  @InjectMocks private UserServiceImpl userService;

  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;
  @Mock private UserStatusRepository userStatusRepository;

  @Test
  void givenExistingUserId_whenGetById_thenReturnUser() {
    User user = UserTestDataUtil.createUser();

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    UserDto userDto = userService.getById(user.getId());

    assertThat(
        userDto,
        allOf(
            hasProperty("id", equalTo(user.getId())),
            hasProperty(
                "userDetail",
                hasProperty("firstName", equalTo(user.getUserDetail().getFirstName()))),
            hasProperty(
                "userDetail", hasProperty("lastName", equalTo(user.getUserDetail().getLastName()))),
            hasProperty(
                "userDetail", hasProperty("balance", equalTo(user.getUserDetail().getBalance()))),
            hasProperty("email", equalTo(user.getEmail())),
            hasProperty("password", equalTo(user.getPassword())),
            hasProperty("status", equalTo(user.getStatus())),
            hasProperty("role", equalTo(user.getRole()))));

    verify(userRepository, times(1)).findById(anyLong());
  }

  @Test
  void givenNotExistingUserId_whenGetById_thenThrowEntityNotFoundException() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.getById(anyLong()))
        .withMessage("Entity is not found");

    verify(userRepository, times(1)).findById(anyLong());
  }

  @Test
  void givenEmptyPageable_whenGetAll_thenReturnListOfAllUsers() {
    UserDto userDto1 = UserTestDataUtil.creatUserDto();

    UserDto userDto2 = UserTestDataUtil.creatUserDto();
    userDto2.setId(2L);

    UserDto userDto3 = UserTestDataUtil.creatUserDto();
    userDto3.setId(3L);

    User user1 = UserMapper.INSTANCE.mapUserDtoToUser(userDto1);
    User user2 = UserMapper.INSTANCE.mapUserDtoToUser(userDto2);
    User user3 = UserMapper.INSTANCE.mapUserDtoToUser(userDto3);

    List<User> users = Arrays.asList(user1, user2, user3);

    Pageable pageable = Pageable.unpaged();

    when(userRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(users, pageable, users.size()));

    Page<UserDto> page = userService.getAll(pageable);

    assertThat(page.getContent(), hasItems(userDto1, userDto2, userDto3));

    verify(userRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenPageable_whenGetAll_thenReturnPageableUsers() {
    UserDto userDto1 = UserTestDataUtil.creatUserDto();

    UserDto userDto2 = UserTestDataUtil.creatUserDto();
    userDto2.setId(2L);

    UserDto userDto3 = UserTestDataUtil.creatUserDto();
    userDto3.setId(3L);

    User user1 = UserMapper.INSTANCE.mapUserDtoToUser(userDto1);
    User user2 = UserMapper.INSTANCE.mapUserDtoToUser(userDto2);
    User user3 = UserMapper.INSTANCE.mapUserDtoToUser(userDto3);

    List<User> users = Arrays.asList(user1, user2, user3);

    Pageable pageable = PageRequest.of(0, 2);

    when(userRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(users.subList(0, 2), pageable, users.size()));

    Page<UserDto> page = userService.getAll(pageable);

    assertThat(page.getSize(), is(2));
    assertThat(page.getPageable(), is(pageable));
    assertThat(page.getContent(), hasItems(userDto1, userDto2));

    verify(userRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenValidUserDto_whenCreate_thenReturnCreatedUserDto() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    Role role = UserTestDataUtil.createSubscriberRole();
    UserStatus status = UserTestDataUtil.createActiveStatus();
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
    when(userStatusRepository.findByName(status.getName())).thenReturn(Optional.of(status));
    when(userRepository.save(expectedConfiguredUser)).thenReturn(expectedConfiguredUser);

    UserDto createdUser = userService.create(inputUserDto);

    assertThat(
        createdUser,
        allOf(
            hasProperty("id", equalTo(expectedConfiguredUser.getId())),
            hasProperty(
                "userDetail",
                hasProperty(
                    "firstName", equalTo(expectedConfiguredUser.getUserDetail().getFirstName()))),
            hasProperty(
                "userDetail",
                hasProperty(
                    "lastName", equalTo(expectedConfiguredUser.getUserDetail().getLastName()))),
            hasProperty(
                "userDetail",
                hasProperty(
                    "balance", equalTo(expectedConfiguredUser.getUserDetail().getBalance()))),
            hasProperty("email", equalTo(expectedConfiguredUser.getEmail())),
            hasProperty("password", equalTo(expectedConfiguredUser.getPassword())),
            hasProperty("status", equalTo(expectedConfiguredUser.getStatus())),
            hasProperty("role", equalTo(expectedConfiguredUser.getRole()))));

    verify(roleRepository, times(1)).findByName(role.getName());
    verify(userStatusRepository, times(1)).findByName(status.getName());
    verify(userRepository, times(1)).save(expectedConfiguredUser);
  }

  @Test
  void givenUserDtoWithInvalidRole_whenCreate_thenThrowEntityNotFoundException() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    Role role = UserTestDataUtil.createSubscriberRole();
    role.setId(1000L);
    UserStatus status = UserTestDataUtil.createActiveStatus();
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.create(inputUserDto))
        .withMessage("Entity is not found");

    verify(roleRepository, times(1)).findByName(role.getName());
    verify(userStatusRepository, times(0)).findByName(status.getName());
    verify(userRepository, times(0)).save(expectedConfiguredUser);
  }

  @Test
  void givenUserDtoWithInvalidUserStatus_whenCreate_thenThrowEntityNotFoundException() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    Role role = UserTestDataUtil.createSubscriberRole();
    UserStatus status = UserTestDataUtil.createActiveStatus();
    status.setId(1000L);
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
    when(userStatusRepository.findByName(status.getName())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.create(inputUserDto))
        .withMessage("Entity is not found");

    verify(roleRepository, times(1)).findByName(role.getName());
    verify(userStatusRepository, times(1)).findByName(status.getName());
    verify(userRepository, times(0)).save(expectedConfiguredUser);
  }

  @Test
  void givenExistingUserId_whenUpdateById_thenReturnUpdatedUser() {
    UserDto userDto = UserTestDataUtil.creatUserDto();
    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

    when(userRepository.existsById(userDto.getId())).thenReturn(true);
    when(userRepository.save(user)).thenReturn(user);

    UserDto updatedUser = userService.updateById(userDto.getId(), userDto);

    assertThat(updatedUser, is(userDto));

    verify(userRepository, times(1)).existsById(userDto.getId());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void givenNotExistingUserId_whenUpdateById_thenThrowEntityNotFoundException() {
    UserDto userDto = UserTestDataUtil.creatUserDto();

    when(userRepository.existsById(userDto.getId())).thenReturn(false);

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.updateById(userDto.getId(), userDto))
        .withMessage("User with id " + userDto.getId() + " is not found");

    verify(userRepository, times(1)).existsById(userDto.getId());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void givenUserId_whenDeleteById_thenCallRepositoryMethod() {
    doNothing().when(userRepository).deleteById(anyLong());

    userService.deleteById(1L);

    verify(userRepository, times(1)).deleteById(1L);
  }

  @Test
  void givenExistingUserEmail_whenGetByEmail_thenReturnUser() {
    User user = UserTestDataUtil.createUser();

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    UserDto userDto = userService.getByEmail(user.getEmail());

    assertThat(
        userDto,
        allOf(
            hasProperty("id", equalTo(user.getId())),
            hasProperty(
                "userDetail",
                hasProperty("firstName", equalTo(user.getUserDetail().getFirstName()))),
            hasProperty(
                "userDetail", hasProperty("lastName", equalTo(user.getUserDetail().getLastName()))),
            hasProperty(
                "userDetail", hasProperty("balance", equalTo(user.getUserDetail().getBalance()))),
            hasProperty("email", equalTo(user.getEmail())),
            hasProperty("password", equalTo(user.getPassword())),
            hasProperty("status", equalTo(user.getStatus())),
            hasProperty("role", equalTo(user.getRole()))));

    verify(userRepository, times(1)).findByEmail(anyString());
  }

  @Test
  void givenNotExistingUserEmail_whenGetByEmail_thenThrowEntityNotFoundException() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.getByEmail(anyString()))
        .withMessage("Entity is not found");

    verify(userRepository, times(1)).findByEmail(anyString());
  }

  @Test
  void givenExistingUserIdOfSubscriber_whenAddFunds_thenReturnUserWithAddedFunds() {
    User userToAddMoney = UserTestDataUtil.createUser();
    BigDecimal amountOfMoneyToAdd = BigDecimal.TEN;
    User userWithMoney = UserTestDataUtil.createUser();
    userWithMoney
        .getUserDetail()
        .setBalance(userToAddMoney.getUserDetail().getBalance().add(amountOfMoneyToAdd));

    when(userRepository.findById(userToAddMoney.getId())).thenReturn(Optional.of(userToAddMoney));
    when(userRepository.save(userWithMoney)).thenReturn(userWithMoney);

    UserDto userDto = userService.addFunds(userToAddMoney.getId(), amountOfMoneyToAdd);

    assertThat(
        userDto, hasProperty("userDetail", hasProperty("balance", equalTo(amountOfMoneyToAdd))));

    verify(userRepository, times(1)).findById(userToAddMoney.getId());
    verify(userRepository, times(1)).save(userWithMoney);
  }

  @Test
  void givenNotExistingUserId_whenAddFunds_thenThrowEntityNotFoundException() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.addFunds(1L, BigDecimal.TEN))
        .withMessage("Entity is not found");

    verify(userRepository, times(1)).findById(anyLong());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void
      givenExistingUserIdOfUserWithoutBalance_whenAddFunds_thenThrowEntityIllegalArgumentException() {
    User userToAddMoney = UserTestDataUtil.createUser();
    userToAddMoney.setUserDetail(null);

    when(userRepository.findById(userToAddMoney.getId())).thenReturn(Optional.of(userToAddMoney));

    assertThatExceptionOfType(EntityIllegalArgumentException.class)
        .isThrownBy(() -> userService.addFunds(1L, BigDecimal.TEN))
        .withMessage("User is not allowed to have balance");

    verify(userRepository, times(1)).findById(userToAddMoney.getId());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void givenExistingUserEmail_whenExistsByEmail_thenReturnTrue() {
    when(userRepository.existsByEmail(anyString())).thenReturn(true);

    boolean isExistByEmail = userService.existsByEmail(anyString());

    assertThat(isExistByEmail, is(true));

    verify(userRepository, times(1)).existsByEmail(anyString());
  }

  @Test
  void givenNotExistingUserEmail_whenExistsByEmail_thenReturnFalse() {
    when(userRepository.existsByEmail(anyString())).thenReturn(false);

    boolean isExistByEmail = userService.existsByEmail(anyString());

    assertThat(isExistByEmail, is(false));

    verify(userRepository, times(1)).existsByEmail(anyString());
  }
}
