package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import java.util.List;

public interface SubscriptionRepository {
  Subscription createSubscription(Subscription subscription);

  List<Subscription> getAllSubscriptionsByUserId(long userId);
}
