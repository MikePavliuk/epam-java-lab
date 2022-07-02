package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;

import java.util.List;

public interface SubscriptionService {
	UserDto addSubscriptionToUser(long userId, long publicationId, int periodInMonths);

	List<Subscription> getAllSubscriptionsByUserId(long userId);
}
