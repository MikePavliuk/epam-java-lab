package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository {

  void subscribeUserToPublication(
      long userId, long publicationId, int periodInMonths, LocalDate startDate);

  List<Subscription> getAllSubscriptionsByUserId(long userId);
}
