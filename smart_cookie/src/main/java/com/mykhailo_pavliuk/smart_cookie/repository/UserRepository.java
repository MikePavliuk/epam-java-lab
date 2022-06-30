package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.User;

import java.util.List;

public interface UserRepository {
	User getUser(String email);

	List<User> getUsers();

	User createUser(User user);

	User updateUser(String email, User user);

	void deleteUser(String email);
}
