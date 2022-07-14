package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

  private final List<Subscription> list = new ArrayList<>();

  @Override
  public void subscribeUserToPublication(
      long userId, long publicationId, int periodInMonths, LocalDate startDate) {
    log.info("Subscribing user to publication");
    list.add(
        Subscription.builder()
            .userId(userId)
            .publicationId(publicationId)
            .periodInMonths(periodInMonths)
            .startDate(LocalDate.now())
            .build());
  }

  @Override
  public List<Subscription> getAllSubscriptionsByUserId(long userId) {
    log.info("Getting all subscriptions by user id");
    return list.stream()
        .filter(subscription -> subscription.getUserId() == userId)
        .collect(Collectors.toList());
  }
}
