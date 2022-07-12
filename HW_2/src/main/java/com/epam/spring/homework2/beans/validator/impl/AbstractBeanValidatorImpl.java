package com.epam.spring.homework2.beans.validator.impl;

import com.epam.spring.homework2.beans.AbstractBean;
import com.epam.spring.homework2.beans.validator.BeanValidator;
import org.springframework.stereotype.Component;

@Component
public class AbstractBeanValidatorImpl implements BeanValidator<AbstractBean> {

  public static final String DEFAULT_NAME = "Not assigned yet";
  public static final int DEFAULT_VALUE = 1;

  @Override
  public void validate(AbstractBean abstractBean) {
    if (abstractBean.getName() == null) {
      abstractBean.setName(DEFAULT_NAME);
    }

    if (abstractBean.getValue() <= 0) {
      abstractBean.setValue(DEFAULT_VALUE);
    }
  }
}
