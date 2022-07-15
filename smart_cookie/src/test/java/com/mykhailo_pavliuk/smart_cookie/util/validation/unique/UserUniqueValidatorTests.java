package com.mykhailo_pavliuk.smart_cookie.util.validation.unique;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserUniqueValidatorTests {

  @InjectMocks private UserUniqueValidator userUniqueValidator;

  @Mock private UserService userService;
  @Mock private ConstraintValidatorContext context;

  @Test
  void givenEmailThatExists_whenIsValid_thenReturnFalse() {
    String email = UserTestDataUtil.EMAIL;

    when(userService.existsByEmail(email)).thenReturn(true);

    boolean isValidResult = userUniqueValidator.isValid(email, context);

    assertFalse(isValidResult);

    verify(userService, times(1)).existsByEmail(email);
  }

  @Test
  void givenUniqueEmail_whenIsValid_thenReturnTrue() {
    String email = UserTestDataUtil.EMAIL;

    when(userService.existsByEmail(email)).thenReturn(false);

    boolean isValidResult = userUniqueValidator.isValid(email, context);

    assertTrue(isValidResult);

    verify(userService, times(1)).existsByEmail(email);
  }
}
