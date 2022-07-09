package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  long countUsersByStatus(String statusName);
}
