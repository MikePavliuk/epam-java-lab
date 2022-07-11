package com.mykhailo_pavliuk.smart_cookie.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.SubscriptionDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.impl.SubscriptionServiceImpl;
import com.mykhailo_pavliuk.smart_cookie.test.util.PublicationTestDataUtil;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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
  void givenExistingIdsAndUserWithEnoughMoney_whenAddSubscription_thenReturnUserWithSubscription() {
    User user = UserTestDataUtil.createUser();
    user.getUserDetail().setBalance(BigDecimal.valueOf(100));

    Publication publication = PublicationTestDataUtil.createPublication();

    int periodInMonths = 3;

    LocalDate startDate = LocalDate.now();

    UserDto userDtoWithSubscription = UserMapper.INSTANCE.mapUserToUserDto(user);
    userDtoWithSubscription.setSubscriptions(
        Collections.singletonList(
            SubscriptionDto.builder()
                .userId(user.getId())
                .publicationId(publication.getId())
                .periodInMonths(periodInMonths)
                .startDate(startDate)
                .build()));
    userDtoWithSubscription
        .getUserDetail()
        .setBalance(
            user.getUserDetail()
                .getBalance()
                .subtract(
                    publication.getPricePerMonth().multiply(BigDecimal.valueOf(periodInMonths))));
    User userWithSubscriptions = UserMapper.INSTANCE.mapUserDtoToUser(userDtoWithSubscription);
    userWithSubscriptions.setSubscriptions(
        Collections.singletonList(
            Subscription.builder()
                .user(user)
                .publication(publication)
                .periodInMonths(periodInMonths)
                .startDate(startDate)
                .build()));

    when(userRepository.findById(user.getId()))
        .thenReturn(Optional.of(user))
        .thenReturn(Optional.of(userWithSubscriptions));
    when(publicationRepository.findById(publication.getId())).thenReturn(Optional.of(publication));

    UserDto resultUserDto =
        subscriptionService.addSubscriptionToUser(
            user.getId(), publication.getId(), periodInMonths);

    assertThat(resultUserDto, is(userDtoWithSubscription));

    verify(userRepository, times(1)).save(user);
    verify(subscriptionRepository, times(1))
        .subscribeUserToPublication(user.getId(), publication.getId(), periodInMonths, startDate);
    verify(userRepository, times(2)).findById(user.getId());
  }
}
