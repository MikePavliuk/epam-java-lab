package com.epam.spring.homework2.beans;

public class BeanC extends AbstractBean implements Initiable, Destroyable {

  public BeanC(String name, int value) {
    super(name, value);
  }

  @Override
  public void customInit() {
    System.out.println("BeanC was initiated by initMethod");
  }

  @Override
  public void customDestroy() {
    System.out.println("BeanC was destroyed by destroyMethod");
  }
}
