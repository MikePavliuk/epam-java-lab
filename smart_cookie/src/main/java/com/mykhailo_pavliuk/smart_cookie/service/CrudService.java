package com.mykhailo_pavliuk.smart_cookie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T, I> {

  T getById(I id);

  Page<T> getAll(Pageable pageable);

  T create(T publicationDto);

  T updateById(I id, T dto);

  void deleteById(I id);
}
