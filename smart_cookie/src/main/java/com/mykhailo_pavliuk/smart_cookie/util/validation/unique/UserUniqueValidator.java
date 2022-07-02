package com.mykhailo_pavliuk.smart_cookie.util.validation.unique;

import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UserUniqueValidator implements ConstraintValidator<Unique,String> {

	private UserService userService;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return !userService.existsByEmail(email);
	}
}
