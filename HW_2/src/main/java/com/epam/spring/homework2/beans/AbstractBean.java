package com.epam.spring.homework2.beans;

public abstract class AbstractBean {
  private String name;
  private int value;

  public AbstractBean() {}

  public AbstractBean(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
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
}
