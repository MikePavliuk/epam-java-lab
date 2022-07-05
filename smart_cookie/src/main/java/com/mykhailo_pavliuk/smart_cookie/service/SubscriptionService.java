package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;

public interface SubscriptionService {
  UserDto addSubscriptionToUser(long userId, long publicationId, int periodInMonths);
}
