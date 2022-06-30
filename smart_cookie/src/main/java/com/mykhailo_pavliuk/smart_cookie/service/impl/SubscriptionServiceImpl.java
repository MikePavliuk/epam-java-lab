package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final UserRepository userRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PublicationRepository publicationRepository;

	@Override
	public UserDto addSubscriptionToUser(String userEmail, Subscription subscription) {
		log.info("Subscribe user with email {} to publication with id {}", userEmail, subscription.getPublicationId());
		User user = userRepository.getUser(userEmail);
		BigDecimal fullPrice = publicationRepository.getPublication(subscription.getPublicationId())
				.getPricePerMonth()
				.multiply(BigDecimal.valueOf(subscription.getPeriodInMonths()));

		if (user.getUserDetail().getBalance().compareTo(fullPrice) < 0) {
			throw new IllegalArgumentException("Not enough money to make a transaction");
		}

		user.getUserDetail().setBalance(user.getUserDetail().getBalance().subtract(fullPrice));
		userRepository.updateUser(userEmail, user);
		Subscription newSubscription = subscriptionRepository.createSubscription(subscription);
		user.getSubscriptions().add(newSubscription);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}
}
