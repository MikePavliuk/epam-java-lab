package com.epam.spring.homework2.beans;

public class BeanB extends AbstractBean implements Initiable, Destroyable {

  public BeanB(String name, int value) {
    super(name, value);
  }

  @Override
  public void customInit() {
    System.out.println("BeanB was initiated by initMethod");
  }

  @Override
  public void customDestroy() {
    System.out.println("BeanB was destroyed by destroyMethod");
  }

  public void anotherCustomInitMethod() {
    System.out.println("BeanB was initiated by ANOTHER initMethod!!!");
  }
}
