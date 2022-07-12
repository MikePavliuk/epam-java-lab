package com.epam.spring.homework2;

import com.epam.spring.homework2.beans.AbstractBean;
import com.epam.spring.homework2.config.BeansConfig;
import java.util.Arrays;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BeansConfig.class);

    Arrays.stream(context.getBeanDefinitionNames())
        .map(context::getBean)
        .filter(AbstractBean.class::isInstance)
        .forEach(System.out::println);

    Arrays.stream(context.getBeanDefinitionNames())
        .map(context::getBeanDefinition)
        .forEach(System.out::println);

    context.close();
  }
}
