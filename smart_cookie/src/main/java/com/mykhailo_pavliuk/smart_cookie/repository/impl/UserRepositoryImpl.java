package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.exception.EntityIllegalArgumentException;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {
  private final List<User> list = new ArrayList<>();

  @Override
  public Optional<User> findById(Long id) {
    log.info("Finding user by id");
    return list.stream().filter(user -> user.getId().equals(id)).findFirst();
  }

  @Override
  public List<User> getAll() {
    log.info("Getting all users");
    return new ArrayList<>(list);
  }

  @Override
  public User save(User user) {
    log.info("Saving user");

    if (user.getId() != null) {
      list.removeIf(u -> Objects.equals(u.getId(), user.getId()));
      list.add(0, user);
      log.info("Finished with updating user");
    } else {

      if (existsByEmail(user.getEmail())) {
        throw new EntityIllegalArgumentException(
            "User with email '" + user.getEmail() + "' is already exists!");
      }

      user.setId(list.isEmpty() ? 1L : (list.get(list.size() - 1).getId() + 1));
      user.getUserDetail()
          .setId(list.isEmpty() ? 1L : (list.get(list.size() - 1).getUserDetail().getId() + 1));
      list.add(user);
      log.info("Finished with creating new user");
    }

    return user;
  }

  @Override
  public void deleteById(Long id) {
    log.info("Deleting user");
    list.removeIf(user -> user.getId().equals(id));
  }

  @Override
  public boolean existsById(Long id) {
    log.info("Checking if user exists by id");
    return list.stream().anyMatch(user -> user.getId().equals(id));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    log.info("Finding user by email");
    return list.stream().filter(user -> user.getEmail().equals(email)).findFirst();
  }

  @Override
  public boolean existsByEmail(String email) {
    log.info("Checking if user exists by email");
    return list.stream().anyMatch(user -> user.getEmail().equals(email));
  }
}
