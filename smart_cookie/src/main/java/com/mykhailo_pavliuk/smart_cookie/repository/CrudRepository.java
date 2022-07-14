package com.mykhailo_pavliuk.smart_cookie.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, I> {

  Optional<T> findById(I id);

  List<T> getAll();

  T save(T entity);

  void deleteById(I id);

  boolean existsById(I id);
}
