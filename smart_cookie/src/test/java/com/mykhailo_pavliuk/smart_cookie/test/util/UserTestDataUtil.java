package com.mykhailo_pavliuk.smart_cookie.test.util;

import com.mykhailo_pavliuk.smart_cookie.dto.RoleDto;
import com.mykhailo_pavliuk.smart_cookie.dto.SubscriptionDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDetailDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserStatusDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.model.UserDetail;
import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestDataUtil {

  public static final Long ID = 1L;
  public static final String FIRST_NAME = "Mykhailo";
  public static final String LAST_NAME = "Pavliuk";
  public static final BigDecimal BALANCE = BigDecimal.ZERO;
  public static final String EMAIL = "mike@gmail.com";
  public static final String PASSWORD = "Mike12345!";
  public static final UserStatus USER_STATUS = createActiveStatus();
  public static final Role ROLE = createSubscriberRole();
  public static final int SUBSCRIPTION_PERIOD_IN_MONTHS = 3;
  public static final LocalDate SUBSCRIPTION_START_DATE = LocalDate.now();

  public static User createUser() {
    return User.builder()
        .id(ID)
        .email(EMAIL)
        .password(PASSWORD)
        .userDetail(
            UserDetail.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .balance(BigDecimal.ZERO)
                .build())
        .role(ROLE)
        .status(USER_STATUS)
        .build();
  }

  public static UserDto creatUserDto() {
    return UserDto.builder()
        .id(ID)
        .email(EMAIL)
        .password(PASSWORD)
        .userDetail(UserDetailDto.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build())
        .build();
  }

  public static UserDto createValidUserDto() {
    UserDto user = creatUserDto();
    user.getUserDetail().setId(1L);
    user.setRole(createSubscriberRole());
    user.setStatus(createActiveStatus());

    return user;
  }

  public static User createUserWithSubscription() {
    User userWithSubscriptions =
        UserMapper.INSTANCE.mapUserDtoToUser(createUserDtoWithSubscription());
    userWithSubscriptions.setSubscriptions(
        Collections.singletonList(
            Subscription.builder()
                .user(UserTestDataUtil.createUser())
                .publication(PublicationTestDataUtil.createPublication())
                .periodInMonths(SUBSCRIPTION_PERIOD_IN_MONTHS)
                .startDate(SUBSCRIPTION_START_DATE)
                .build()));

    return userWithSubscriptions;
  }

  public static UserDto createUserDtoWithSubscription() {
    User user = UserTestDataUtil.createUser();
    user.getUserDetail().setBalance(BigDecimal.valueOf(100));

    UserDto userDtoWithSubscription = UserMapper.INSTANCE.mapUserToUserDto(user);
    userDtoWithSubscription.setSubscriptions(
        Collections.singletonList(
            SubscriptionDto.builder()
                .userId(ID)
                .publicationId(PublicationTestDataUtil.ID)
                .periodInMonths(SUBSCRIPTION_PERIOD_IN_MONTHS)
                .startDate(SUBSCRIPTION_START_DATE)
                .build()));
    userDtoWithSubscription
        .getUserDetail()
        .setBalance(
            user.getUserDetail()
                .getBalance()
                .subtract(
                    PublicationTestDataUtil.PRICE_PER_MONTH.multiply(
                        BigDecimal.valueOf(SUBSCRIPTION_PERIOD_IN_MONTHS))));

    return userDtoWithSubscription;
  }

  private static Role createSubscriberRole() {
    return Role.builder().id(1L).name(RoleDto.SUBSCRIBER.name().toLowerCase()).build();
  }

  private static UserStatus createActiveStatus() {
    return UserStatus.builder().id(1L).name(UserStatusDto.ACTIVE.name().toLowerCase()).build();
  }
}
