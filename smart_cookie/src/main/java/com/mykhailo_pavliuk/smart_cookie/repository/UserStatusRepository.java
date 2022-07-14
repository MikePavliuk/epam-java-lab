package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserStatusRepository extends CrudRepository<UserStatus, Long> {

  Optional<UserStatus> findByName(String name);
}
