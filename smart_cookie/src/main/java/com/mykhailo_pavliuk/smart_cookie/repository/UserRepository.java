package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.User;

import java.util.List;

public interface UserRepository {
	User getUser(long id);

	List<User> getUsers();

	User createUser(User user);

	User updateUser(long id, User user);

	void deleteUser(long id);

	boolean existsByEmail(String email);
}
