package com.epam.spring.homework2.beans;

import com.epam.spring.homework2.Validatable;

public abstract class AbstractBean implements Validatable {
  private String name;
  private int value;

  public AbstractBean() {}

  public AbstractBean(String name, int value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "{"
        + "name='"
        + name
        + '\''
        + ", value='"
        + value
        + '\''
        + '}';
  }

  @Override
  public void validate() {
    if (name == null) {
      name = "Not assigned yet";
    }

    if (value <= 0) {
      value = 1;
    }
  }
}
