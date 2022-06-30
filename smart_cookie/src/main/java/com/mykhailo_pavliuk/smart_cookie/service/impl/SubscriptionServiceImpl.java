package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final UserRepository userRepository;

	@Override
	public UserDto createSubscription(String userEmail, Subscription subscription) {
		log.info("Subscribe user with email {} to publication with id {}", userEmail, subscription.getPublicationId());
		User user = userRepository.getUser(userEmail);
		Subscription newSubscription = subscriptionRepository.createSubscription(subscription);
		user.getSubscriptions().add(newSubscription);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}
}
