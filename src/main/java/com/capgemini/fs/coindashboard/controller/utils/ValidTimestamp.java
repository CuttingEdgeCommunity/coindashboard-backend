package com.capgemini.fs.coindashboard.controller.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidTimestamp.Validator.class})
public @interface ValidTimestamp {

  String message() default "Timestamp must be in range from epoch to now";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class Validator implements ConstraintValidator<ValidTimestamp, Long> {

    @Override
    public void initialize(ValidTimestamp requiredIfChecked) {
    }

    public boolean isValid(Long value, ConstraintValidatorContext context) {
      return (value != null && value >= 0 && value <= System.currentTimeMillis());
    }
  }
}
