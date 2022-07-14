package com.mykhailo_pavliuk.smart_cookie.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.test.util.PublicationTestDataUtil;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTests {

  @InjectMocks private SubscriptionServiceImpl subscriptionService;

  @Mock private UserRepository userRepository;
  @Mock private SubscriptionRepository subscriptionRepository;
  @Mock private PublicationRepository publicationRepository;

  @Test
  void givenValidParameters_whenAddSubscription_thenReturnUserWithSubscription() {
    User userWithoutSubscription = UserTestDataUtil.createUser();
    userWithoutSubscription.getUserDetail().setBalance(BigDecimal.valueOf(100));
    User userWithSubscription = UserTestDataUtil.createUserWithSubscription();
    UserDto userDtoWithSubscription = UserTestDataUtil.createUserDtoWithSubscription();

    when(userRepository.findById(UserTestDataUtil.ID))
        .thenReturn(Optional.of(userWithoutSubscription))
        .thenReturn(Optional.of(userWithSubscription));
    when(publicationRepository.findById(PublicationTestDataUtil.ID))
        .thenReturn(Optional.of(PublicationTestDataUtil.createPublication()));
    when(userRepository.save(userWithoutSubscription)).thenReturn(userWithoutSubscription);

    UserDto resultUserDto =
        subscriptionService.addSubscriptionToUser(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS);

    assertThat(resultUserDto, is(userDtoWithSubscription));

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(1)).save(userWithSubscription);
    verify(subscriptionRepository, times(1))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(2)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenNotExistingUserId_whenAddSubscription_thenThrowEntityNotFoundException() {
    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () ->
                subscriptionService.addSubscriptionToUser(
                    UserTestDataUtil.ID,
                    PublicationTestDataUtil.ID,
                    UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
        .withMessage("User with id '" + UserTestDataUtil.ID + "' is not found!");

    verify(publicationRepository, times(0)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
    verify(subscriptionRepository, times(0))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenNotExistingPublicationId_whenAddSubscription_thenThrowEntityNotFoundException() {
    when(userRepository.findById(UserTestDataUtil.ID))
        .thenReturn(Optional.of(UserTestDataUtil.createUser()));
    when(publicationRepository.findById(PublicationTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () ->
                subscriptionService.addSubscriptionToUser(
                    UserTestDataUtil.ID,
                    PublicationTestDataUtil.ID,
                    UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
        .withMessage("Publication with id '" + PublicationTestDataUtil.ID + "' is not found!");

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
    verify(subscriptionRepository, times(0))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(1)).findById(PublicationTestDataUtil.ID);
  }

  @Test
  void givenUserWithNoUserDetails_whenAddSubscription_thenThrowEntityIllegalArgumentException() {
    User user = UserTestDataUtil.createUser();
    user.setRole(Role.builder().id(2L).name("admin").build());
    user.setUserDetail(null);

    when(userRepository.findById(UserTestDataUtil.ID)).thenReturn(Optional.of(user));
    when(publicationRepository.findById(PublicationTestDataUtil.ID))
        .thenReturn(Optional.of(PublicationTestDataUtil.createPublication()));

    assertThatExceptionOfType(EntityIllegalArgumentException.class)
        .isThrownBy(
            () ->
                subscriptionService.addSubscriptionToUser(
                    UserTestDataUtil.ID,
                    PublicationTestDataUtil.ID,
                    UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
        .withMessage(
            String.format(
                "User is not allowed to have balance! User's role is '%s'.",
                user.getRole().getName()));

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
    verify(subscriptionRepository, times(0))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenUserWithNotEnoughMoney_whenAddSubscription_thenThrowEntityIllegalArgumentException() {
    User userWithoutSubscription = UserTestDataUtil.createUser();
    User userWithSubscription = UserTestDataUtil.createUserWithSubscription();

    when(userRepository.findById(UserTestDataUtil.ID))
        .thenReturn(Optional.of(userWithoutSubscription))
        .thenReturn(Optional.of(userWithSubscription));
    when(publicationRepository.findById(PublicationTestDataUtil.ID))
        .thenReturn(Optional.of(PublicationTestDataUtil.createPublication()));

    assertThatExceptionOfType(EntityIllegalArgumentException.class)
        .isThrownBy(
            () ->
                subscriptionService.addSubscriptionToUser(
                    UserTestDataUtil.ID,
                    PublicationTestDataUtil.ID,
                    UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
        .withMessage(
            "User has not enough money to make a transaction! "
                + PublicationTestDataUtil.PRICE_PER_MONTH.multiply(
                    BigDecimal.valueOf(UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
                + "$ is missing!");

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(0)).save(any());
    verify(subscriptionRepository, times(0))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(1)).findById(UserTestDataUtil.ID);
  }

  @Test
  void givenValidUserButCantGetAfterSave_whenAddSubscription_thenThrowEntityNotFoundException() {
    User userWithoutSubscription = UserTestDataUtil.createUser();
    userWithoutSubscription.getUserDetail().setBalance(BigDecimal.valueOf(100));
    User userWithSubscription = UserTestDataUtil.createUserWithSubscription();

    when(userRepository.findById(UserTestDataUtil.ID))
        .thenReturn(Optional.of(userWithoutSubscription))
        .thenReturn(Optional.empty());
    when(publicationRepository.findById(PublicationTestDataUtil.ID))
        .thenReturn(Optional.of(PublicationTestDataUtil.createPublication()));
    when(userRepository.save(userWithoutSubscription)).thenReturn(userWithoutSubscription);

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () ->
                subscriptionService.addSubscriptionToUser(
                    UserTestDataUtil.ID,
                    PublicationTestDataUtil.ID,
                    UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS))
        .withMessage(
            "Can't find user with id '" + UserTestDataUtil.ID + "' after correct subscription!");

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
    verify(userRepository, times(1)).save(userWithSubscription);
    verify(subscriptionRepository, times(1))
        .subscribeUserToPublication(
            UserTestDataUtil.ID,
            PublicationTestDataUtil.ID,
            UserTestDataUtil.SUBSCRIPTION_PERIOD_IN_MONTHS,
            UserTestDataUtil.SUBSCRIPTION_START_DATE);
    verify(userRepository, times(2)).findById(UserTestDataUtil.ID);
  }
}
