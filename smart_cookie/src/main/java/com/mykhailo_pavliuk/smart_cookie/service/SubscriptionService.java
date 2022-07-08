package com.mykhailo_pavliuk.smart_cookie.service;

public interface SubscriptionService {
  void addSubscriptionToUser(long userId, long publicationId, int periodInMonths);
}
