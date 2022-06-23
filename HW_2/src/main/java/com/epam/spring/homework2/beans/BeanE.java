package com.epam.spring.homework2.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
