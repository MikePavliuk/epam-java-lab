package com.epam.spring.homework1;

import com.epam.spring.homework1.config.BeansConfig;
import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Pet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
		context.getBean(Pet.class).printPets();

		// 10 task

		// get bean by type
		Cheetah cheetah1 = context.getBean("cheetah", Cheetah.class); // we get bean annotated as @Primary (without it would be 2 possible choices, so the exception would occur)

		// get bean by name
		Cheetah cheetah2 = context.getBean("secondCheetah", Cheetah.class); // we get bean by its name, which is actually is the name of the method in config-class

	}

}
