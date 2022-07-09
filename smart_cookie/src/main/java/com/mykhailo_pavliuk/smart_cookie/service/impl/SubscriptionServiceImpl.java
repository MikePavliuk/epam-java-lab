package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
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

  @Override
  public void addSubscriptionToUser(long userId, long publicationId, int periodInMonths) {
    log.info("Started adding subscription to user");
    Optional<User> user = userRepository.findById(userId);
    Optional<Publication> publication = publicationRepository.findById(publicationId);

    if (user.isEmpty() || publication.isEmpty()) {
      throw new EntityNotFoundException();
    }

    if (user.get().getUserDetail() == null) {
      throw new EntityIllegalArgumentException("User is not allowed to have balance");
    }

    BigDecimal fullPrice =
        publication.get().getPricePerMonth().multiply(BigDecimal.valueOf(periodInMonths));

    if (user.get().getUserDetail().getBalance().compareTo(fullPrice) < 0) {
      throw new EntityIllegalArgumentException("Not enough money to make a transaction");
    }

    user.get()
        .getUserDetail()
        .setBalance(user.get().getUserDetail().getBalance().subtract(fullPrice));

    userRepository.save(user.get());

    subscriptionRepository.subscribeUserToPublication(
        userId, publicationId, periodInMonths, LocalDate.now());

    log.info("Finished adding subscription to user");
  }
}
