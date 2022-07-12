package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.BeansScan;
import com.epam.spring.homework2.bpp.BppScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackageClasses = {
      BeansScan.class,
      BppScan.class,
    })
public class AnotherConfig {}
