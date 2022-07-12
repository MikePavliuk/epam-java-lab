package com.epam.spring.homework2.bpp;

import com.epam.spring.homework2.beans.AbstractBean;
import com.epam.spring.homework2.beans.validator.BeanValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

  private final BeanValidator<AbstractBean> beanValidator;

  public CustomBeanPostProcessor(BeanValidator<AbstractBean> beanValidator) {
    this.beanValidator = beanValidator;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

    if (bean instanceof AbstractBean) {
      beanValidator.validate((AbstractBean) bean);
    }

    return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
  }
}
