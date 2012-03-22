package com.em.validation.client.model.defects.defect_68;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, Values> {

		private Set<Values> allowedValues = new HashSet<Values>();
		
		@Override
		public void initialize(final AllowedValues constraintAnnotation) {
			allowedValues.addAll(Arrays.asList(constraintAnnotation.value()));
		}

		@Override
		public boolean isValid(final Values value, final ConstraintValidatorContext context) {
			return value == null || allowedValues.contains(value);
		}
	}