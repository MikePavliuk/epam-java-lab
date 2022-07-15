package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final PublicationRepository publicationRepository;

  @Transactional
  @Override
  public UserDto addSubscriptionToUser(long userId, long publicationId, int periodInMonths) {
    log.info("Started adding subscription to user");
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("User with id '%d' is not found!", userId)));
    Publication publication =
        publicationRepository
            .findById(publicationId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Publication with id '%d' is not found!", publicationId)));

    if (user.getUserDetail() == null) {
      throw new EntityIllegalArgumentException(
          String.format(
              "User is not allowed to have balance! User's role is '%s'.",
              user.getRole().getName()));
    }

    BigDecimal fullPrice =
        publication.getPricePerMonth().multiply(BigDecimal.valueOf(periodInMonths));

    if (user.getUserDetail().getBalance().compareTo(fullPrice) < 0) {
      throw new EntityIllegalArgumentException(
          String.format(
              "User has not enough money to make a transaction! %s$ is missing!",
              fullPrice.subtract(user.getUserDetail().getBalance())));
    }

    user.getUserDetail().setBalance(user.getUserDetail().getBalance().subtract(fullPrice));

    userRepository.save(user);

    subscriptionRepository.subscribeUserToPublication(
        userId, publicationId, periodInMonths, LocalDate.now());

    log.info("Finished adding subscription to user");

    return UserMapper.INSTANCE.mapUserToUserDto(
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(
                            "Can't find user with id '%d' after correct subscription!", userId))));
  }
}
