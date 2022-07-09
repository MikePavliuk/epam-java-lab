package com.mykhailo_pavliuk.smart_cookie.service;

import java.util.List;

public interface CrudService<T, I> {

  T getById(I id);

  List<T> getAll();

  T create(T publicationDto);

  T updateById(I id, T dto);

  void deleteById(I id);
}
