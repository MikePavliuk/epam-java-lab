package com.epam.spring.homework2;

import com.epam.spring.homework2.config.BeansConfig;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
    Arrays.stream(context.getBeanDefinitionNames())
        .forEach(name -> System.out.println(context.getBean(name)));
    ((AnnotationConfigApplicationContext) context).close();
  }
}
