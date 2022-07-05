package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanA extends AbstractBean implements InitializingBean, DisposableBean {

  @Override
  public void afterPropertiesSet() {
    System.out.println("BeanA was initiated by InitializingBean");
  }

  @Override
  public void destroy() {
    System.out.println("BeanA was destroyed by DisposableBean");
  }
}
