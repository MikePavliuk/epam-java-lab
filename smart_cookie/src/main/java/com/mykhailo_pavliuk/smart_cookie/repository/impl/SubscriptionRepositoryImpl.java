package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	private final List<Subscription> list = new ArrayList<>();

	@Override
	public Subscription createSubscription(Subscription subscription) {
		log.info("Create subscription {}", subscription);
		list.add(subscription);
		return subscription;
	}

	@Override
	public List<Subscription> getAllSubscriptionsByUserId(long userId) {
		return list.stream()
				.filter(subscription -> subscription.getUserId() == userId)
				.collect(Collectors.toList());
	}
}
