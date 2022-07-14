package com.mykhailo_pavliuk.smart_cookie.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.RoleRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserStatusRepository;
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
  void givenExistingUserId_whenGetById_thenReturnGotUserDto() {
    User user = UserTestDataUtil.createUser();

    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.of(user));

    UserDto gotUserDto = userService.getById(UserTestDataUtil.ID);

    assertThat(
        gotUserDto,
        allOf(
            hasProperty("id", equalTo(UserTestDataUtil.ID)),
            hasProperty(
                "userDetail", hasProperty("firstName", equalTo(UserTestDataUtil.FIRST_NAME))),
            hasProperty("userDetail", hasProperty("lastName", equalTo(UserTestDataUtil.LAST_NAME))),
            hasProperty("userDetail", hasProperty("balance", equalTo(UserTestDataUtil.BALANCE))),
            hasProperty("email", equalTo(UserTestDataUtil.EMAIL)),
            hasProperty("password", equalTo(UserTestDataUtil.PASSWORD)),
            hasProperty("status", equalTo(UserTestDataUtil.USER_STATUS)),
            hasProperty("role", equalTo(UserTestDataUtil.ROLE))));

    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenNotExistingUserId_whenGetById_thenThrowEntityNotFoundException() {
    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.getById(UserTestDataUtil.ID))
        .withMessage("User with id '" + UserTestDataUtil.ID + "' is not found!");

    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenEmptyPageable_whenGetAll_thenReturnListOfAllUsersInPage() {
    UserDto userDto1 = UserTestDataUtil.creatUserDto();

    UserDto userDto2 = UserTestDataUtil.creatUserDto();
    userDto2.setId(2L);

    UserDto userDto3 = UserTestDataUtil.creatUserDto();
    userDto3.setId(3L);

    List<User> users =
        Arrays.asList(
            UserMapper.INSTANCE.mapUserDtoToUser(userDto1),
            UserMapper.INSTANCE.mapUserDtoToUser(userDto2),
            UserMapper.INSTANCE.mapUserDtoToUser(userDto3));

    Pageable pageable = Pageable.unpaged();

    when(userRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(users, pageable, users.size()));

    Page<UserDto> resultPageableUsers = userService.getAll(pageable);

    assertThat(resultPageableUsers.getContent(), hasItems(userDto1, userDto2, userDto3));

    verify(userRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenPageable_whenGetAll_thenReturnPageableUsers() {
    UserDto userDto1 = UserTestDataUtil.creatUserDto();

    UserDto userDto2 = UserTestDataUtil.creatUserDto();
    userDto2.setId(2L);

    UserDto userDto3 = UserTestDataUtil.creatUserDto();
    userDto3.setId(3L);

    List<User> users =
        Arrays.asList(
            UserMapper.INSTANCE.mapUserDtoToUser(userDto1),
            UserMapper.INSTANCE.mapUserDtoToUser(userDto2),
            UserMapper.INSTANCE.mapUserDtoToUser(userDto3));

    Pageable pageable = PageRequest.of(0, 2);

    when(userRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(users.subList(0, 2), pageable, users.size()));

    Page<UserDto> resultPageableUsers = userService.getAll(pageable);

    assertThat(resultPageableUsers.getSize(), is(2));
    assertThat(resultPageableUsers.getPageable(), is(pageable));
    assertThat(resultPageableUsers.getContent(), hasItems(userDto1, userDto2));

    verify(userRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenValidUserDto_whenCreate_thenReturnCreatedUserDto() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(UserTestDataUtil.ROLE.getName()))
        .thenReturn(Optional.of(UserTestDataUtil.ROLE));
    when(userStatusRepository.findByName(UserTestDataUtil.USER_STATUS.getName()))
        .thenReturn(Optional.of(UserTestDataUtil.USER_STATUS));
    when(userRepository.save(expectedConfiguredUser)).thenReturn(expectedConfiguredUser);

    UserDto createdUserDto = userService.create(inputUserDto);

    assertThat(
        createdUserDto,
        allOf(
            hasProperty("id", equalTo(UserTestDataUtil.ID)),
            hasProperty(
                "userDetail", hasProperty("firstName", equalTo(UserTestDataUtil.FIRST_NAME))),
            hasProperty("userDetail", hasProperty("lastName", equalTo(UserTestDataUtil.LAST_NAME))),
            hasProperty("userDetail", hasProperty("balance", equalTo(UserTestDataUtil.BALANCE))),
            hasProperty("email", equalTo(UserTestDataUtil.EMAIL)),
            hasProperty("password", equalTo(UserTestDataUtil.PASSWORD)),
            hasProperty("status", equalTo(UserTestDataUtil.USER_STATUS)),
            hasProperty("role", equalTo(UserTestDataUtil.ROLE))));

    verify(roleRepository, times(1)).findByName(UserTestDataUtil.ROLE.getName());
    verify(userStatusRepository, times(1)).findByName(UserTestDataUtil.USER_STATUS.getName());
    verify(userRepository, times(1)).save(expectedConfiguredUser);
  }

  @Test
  void givenUserDtoWithInvalidRole_whenCreate_thenThrowEntityNotFoundException() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(UserTestDataUtil.ROLE.getName())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.create(inputUserDto))
        .withMessage("Role '" + UserTestDataUtil.ROLE.getName() + "' is not found!");

    verify(roleRepository, times(1)).findByName(UserTestDataUtil.ROLE.getName());
    verify(userStatusRepository, times(0)).findByName(UserTestDataUtil.USER_STATUS.getName());
    verify(userRepository, times(0)).save(expectedConfiguredUser);
  }

  @Test
  void givenUserDtoWithInvalidUserStatus_whenCreate_thenThrowEntityNotFoundException() {
    UserDto inputUserDto = UserTestDataUtil.creatUserDto();
    User expectedConfiguredUser = UserTestDataUtil.createUser();

    when(roleRepository.findByName(UserTestDataUtil.ROLE.getName()))
        .thenReturn(Optional.of(UserTestDataUtil.ROLE));
    when(userStatusRepository.findByName(UserTestDataUtil.USER_STATUS.getName()))
        .thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.create(inputUserDto))
        .withMessage("User status '" + UserTestDataUtil.USER_STATUS.getName() + "' is not found!");

    verify(roleRepository, times(1)).findByName(UserTestDataUtil.ROLE.getName());
    verify(userStatusRepository, times(1)).findByName(UserTestDataUtil.USER_STATUS.getName());
    verify(userRepository, times(0)).save(expectedConfiguredUser);
  }

  @Test
  void givenExistingUserId_whenUpdateById_thenReturnUpdatedUser() {
    UserDto userDto = UserTestDataUtil.creatUserDto();
    User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

    when(userRepository.existsById(UserTestDataUtil.ID)).thenReturn(true);
    when(userRepository.save(user)).thenReturn(user);

    UserDto updatedUser = userService.updateById(UserTestDataUtil.ID, userDto);

    assertThat(updatedUser, is(userDto));

    verify(userRepository, times(1)).existsById(UserTestDataUtil.ID);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void givenNotExistingUserId_whenUpdateById_thenThrowEntityNotFoundException() {
    UserDto userDto = UserTestDataUtil.creatUserDto();

    when(userRepository.existsById(UserTestDataUtil.ID)).thenReturn(false);

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.updateById(UserTestDataUtil.ID, userDto))
        .withMessage("User with id '" + UserTestDataUtil.ID + "' is not found!");

    verify(userRepository, times(1)).existsById(UserTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void givenUserId_whenDeleteById_thenCallRepositoryMethod() {
    doNothing().when(userRepository).deleteById(UserTestDataUtil.ID);

    userService.deleteById(UserTestDataUtil.ID);

    verify(userRepository, times(1)).deleteById(UserTestDataUtil.ID);
  }

  @Test
  void givenExistingUserEmail_whenGetByEmail_thenReturnGotUserDto() {
    User user = UserTestDataUtil.createUser();

    when(userRepository.findByEmail(UserTestDataUtil.EMAIL)).thenReturn(Optional.of(user));

    UserDto gotUserDto = userService.getByEmail(UserTestDataUtil.EMAIL);

    assertThat(
        gotUserDto,
        allOf(
            hasProperty("id", equalTo(UserTestDataUtil.ID)),
            hasProperty(
                "userDetail", hasProperty("firstName", equalTo(UserTestDataUtil.FIRST_NAME))),
            hasProperty("userDetail", hasProperty("lastName", equalTo(UserTestDataUtil.LAST_NAME))),
            hasProperty("userDetail", hasProperty("balance", equalTo(UserTestDataUtil.BALANCE))),
            hasProperty("email", equalTo(UserTestDataUtil.EMAIL)),
            hasProperty("password", equalTo(UserTestDataUtil.PASSWORD)),
            hasProperty("status", equalTo(UserTestDataUtil.USER_STATUS)),
            hasProperty("role", equalTo(UserTestDataUtil.ROLE))));

    verify(userRepository, times(1)).findByEmail(UserTestDataUtil.EMAIL);
  }

  @Test
  void givenNotExistingUserEmail_whenGetByEmail_thenThrowEntityNotFoundException() {
    when(userRepository.findByEmail(UserTestDataUtil.EMAIL)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.getByEmail(UserTestDataUtil.EMAIL))
        .withMessage("User with email '" + UserTestDataUtil.EMAIL + "' is not found!");

    verify(userRepository, times(1)).findByEmail(UserTestDataUtil.EMAIL);
  }

  @Test
  void givenExistingUserIdOfSubscriber_whenAddFunds_thenReturnUserWithAddedFunds() {
    User userToAddMoney = UserTestDataUtil.createUser();
    BigDecimal amountOfMoneyToAdd = BigDecimal.TEN;
    User userWithMoney = UserTestDataUtil.createUser();
    userWithMoney
        .getUserDetail()
        .setBalance(userToAddMoney.getUserDetail().getBalance().add(amountOfMoneyToAdd));

    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.of(userToAddMoney));
    when(userRepository.save(userWithMoney)).thenReturn(userWithMoney);

    UserDto userDto = userService.addFunds(UserTestDataUtil.ID, amountOfMoneyToAdd);

    assertThat(
        userDto, hasProperty("userDetail", hasProperty("balance", equalTo(amountOfMoneyToAdd))));

    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
    verify(userRepository, times(1)).save(userWithMoney);
  }

  @Test
  void givenNotExistingUserId_whenAddFunds_thenThrowEntityNotFoundException() {
    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.addFunds(UserTestDataUtil.ID, BigDecimal.TEN))
        .withMessage("User with id '" + UserTestDataUtil.ID + "' is not found!");

    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void
      givenExistingUserIdOfUserWithoutBalance_whenAddFunds_thenThrowEntityIllegalArgumentException() {
    User userToAddMoney = UserTestDataUtil.createUser();
    userToAddMoney.setUserDetail(null);

    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.of(userToAddMoney));

    assertThatExceptionOfType(EntityIllegalArgumentException.class)
        .isThrownBy(() -> userService.addFunds(UserTestDataUtil.ID, BigDecimal.TEN))
        .withMessage("User is not allowed to have balance!");

    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
  }

  @Test
  void givenExistingUserEmail_whenExistsByEmail_thenReturnTrue() {
    when(userRepository.existsByEmail(UserTestDataUtil.EMAIL)).thenReturn(true);

    boolean isExistByEmail = userService.existsByEmail(UserTestDataUtil.EMAIL);

    assertThat(isExistByEmail, is(true));

    verify(userRepository, times(1)).existsByEmail(UserTestDataUtil.EMAIL);
  }

  @Test
  void givenNotExistingUserEmail_whenExistsByEmail_thenReturnFalse() {
    when(userRepository.existsByEmail(UserTestDataUtil.EMAIL)).thenReturn(false);

    boolean isExistByEmail = userService.existsByEmail(UserTestDataUtil.EMAIL);

    assertThat(isExistByEmail, is(false));

    verify(userRepository, times(1)).existsByEmail(UserTestDataUtil.EMAIL);
  }
}
