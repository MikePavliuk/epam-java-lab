package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  boolean existsByEmail(String email);
}
