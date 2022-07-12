package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class BeanE extends AbstractBean {

  @PostConstruct
  private void initBeanE() {
    System.out.println("BeanE was initiated by @PostConstruct");
  }

  @PreDestroy
  private void destroyBeanE() {
    System.out.println("BeanE was destroyed by @PreDestroy");
  }
}
