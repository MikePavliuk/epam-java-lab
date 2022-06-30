package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {
	private final List<User> list = new ArrayList<>();

	@Override
	public User getUser(String email) {
		log.info("Get user with email {}", email);
		return list.stream()
				.filter(user -> user.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("User is not found!"));
	}

	@Override
	public List<User> getUsers() {
		log.info("Get all users");
		return new ArrayList<>(list);
	}

	@Override
	public User createUser(User user) {
		log.info("Create user {}", user);
		list.add(user);
		return user;
	}

	@Override
	public User updateUser(String email, User user) {
		log.info("Update user with email {}", email);
		boolean isDeleted = list.removeIf(u -> u.getEmail().equals(email));
		if (isDeleted) {
			list.add(user);
		} else {
			throw new RuntimeException("User is not found!");
		}
		return user;
	}

	@Override
	public void deleteUser(String email) {
		log.info("Delete user with email {}", email);
		list.removeIf(user -> user.getEmail().equals(email));
	}
}
