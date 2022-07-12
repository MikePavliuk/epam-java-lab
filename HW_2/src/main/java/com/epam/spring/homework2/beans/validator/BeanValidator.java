package com.epam.spring.homework2.beans.validator;

public interface BeanValidator<T> {

  void validate(T target);
}
