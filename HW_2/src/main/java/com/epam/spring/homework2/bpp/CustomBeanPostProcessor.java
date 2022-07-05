package com.epam.spring.homework2.bpp;

import com.epam.spring.homework2.Validatable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

    if (bean instanceof Validatable) {
      ((Validatable) bean).validate();
    }

    return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
  }
}
