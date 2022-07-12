package com.epam.spring.homework2.beans;

public class BeanD extends AbstractBean implements Initiable, Destroyable {

  public BeanD(String name, int value) {
    super(name, value);
  }

  @Override
  public void customInit() {
    System.out.println("BeanD was initiated by initMethod");
  }

  @Override
  public void customDestroy() {
    System.out.println("BeanD was destroyed by destroyMethod");
  }
}
