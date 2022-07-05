package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Import(AnotherConfig.class)
@PropertySource("classpath:application.properties")
public class BeansConfig {

  @Autowired private Environment env;

  @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
  @DependsOn("beanD")
  public BeanB beanB() {
    return new BeanB(
        env.getProperty("beanB.name"),
        getBeanValueFromPropertiesElseReturnMinInteger("beanB.value"));
  }

  @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
  @DependsOn("beanB")
  public BeanC beanC() {
    return new BeanC(
        env.getProperty("beanC.name"),
        getBeanValueFromPropertiesElseReturnMinInteger("beanC.value"));
  }

  @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
  public BeanD beanD() {
    return new BeanD(
        env.getProperty("beanD.name"),
        getBeanValueFromPropertiesElseReturnMinInteger("beanD.value"));
  }

  private int getBeanValueFromPropertiesElseReturnMinInteger(String property) {
    if (env.getProperty(property) != null) {
      return Integer.parseInt(env.getProperty(property));
    }

    return Integer.MIN_VALUE;
  }
}
