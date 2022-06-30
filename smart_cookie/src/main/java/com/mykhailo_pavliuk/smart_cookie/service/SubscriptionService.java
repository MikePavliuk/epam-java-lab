package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;

public interface SubscriptionService {
	UserDto addSubscriptionToUser(String userEmail, Subscription subscription);
}
