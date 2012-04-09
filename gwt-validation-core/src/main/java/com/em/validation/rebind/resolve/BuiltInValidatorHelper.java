package com.em.validation.rebind.resolve;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.em.validation.client.validators.AssertFalseBooleanValidator;
import com.em.validation.client.validators.AssertTrueBooleanValidator;
import com.em.validation.client.validators.DigitsNumberValidator;
import com.em.validation.client.validators.FutureDateValidator;
import com.em.validation.client.validators.NotNullObjectValidator;
import com.em.validation.client.validators.NullObjectValidator;
import com.em.validation.client.validators.PastDateValdiator;
import com.em.validation.client.validators.PatternStringValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxBigDecimalValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxBigIntegerValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxByteValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxDoubleValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxFloatValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxIntegerValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxLongValidator;
import com.em.validation.client.validators.decimalmax.DecimalMaxShortValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinBigDecimalValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinBigIntegerValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinByteValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinDoubleValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinFloatValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinIntegerValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinLongValidator;
import com.em.validation.client.validators.decimalmin.DecimalMinShortValidator;
import com.em.validation.client.validators.max.MaxBigDecimalValidator;
import com.em.validation.client.validators.max.MaxBigIntegerValidator;
import com.em.validation.client.validators.max.MaxByteValidator;
import com.em.validation.client.validators.max.MaxDoubleValidator;
import com.em.validation.client.validators.max.MaxFloatValidator;
import com.em.validation.client.validators.max.MaxIntegerValidator;
import com.em.validation.client.validators.max.MaxLongValidator;
import com.em.validation.client.validators.max.MaxShortValidator;
import com.em.validation.client.validators.min.MinBigDecimalValidator;
import com.em.validation.client.validators.min.MinBigIntegerValidator;
import com.em.validation.client.validators.min.MinByteValidator;
import com.em.validation.client.validators.min.MinDoubleValidator;
import com.em.validation.client.validators.min.MinFloatValidator;
import com.em.validation.client.validators.min.MinIntegerValidator;
import com.em.validation.client.validators.min.MinLongValidator;
import com.em.validation.client.validators.min.MinShortValidator;
import com.em.validation.client.validators.size.SizeArrayValidator;
import com.em.validation.client.validators.size.SizeCollectionValidator;
import com.em.validation.client.validators.size.SizeMapValidator;
import com.em.validation.client.validators.size.SizeStringValidator;

public class BuiltInValidatorHelper {

	public static Set<Class<?>> getBuiltInValidators(Class<?> constraintAnnotationClass) {
		
		Set<Class<?>> builtInValidators = new HashSet<Class<?>>();
		
		if(AssertFalse.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(AssertFalseBooleanValidator.class);
		} else if(AssertTrue.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(AssertTrueBooleanValidator.class);
		} else if(DecimalMax.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(DecimalMaxBigDecimalValidator.class);
			builtInValidators.add(DecimalMaxBigIntegerValidator.class);
			builtInValidators.add(DecimalMaxByteValidator.class);
			builtInValidators.add(DecimalMaxDoubleValidator.class);
			builtInValidators.add(DecimalMaxFloatValidator.class);
			builtInValidators.add(DecimalMaxIntegerValidator.class);
			builtInValidators.add(DecimalMaxLongValidator.class);
			builtInValidators.add(DecimalMaxShortValidator.class);			
		} else if(DecimalMin.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(DecimalMinBigDecimalValidator.class);
			builtInValidators.add(DecimalMinBigIntegerValidator.class);
			builtInValidators.add(DecimalMinByteValidator.class);
			builtInValidators.add(DecimalMinDoubleValidator.class);
			builtInValidators.add(DecimalMinFloatValidator.class);
			builtInValidators.add(DecimalMinIntegerValidator.class);
			builtInValidators.add(DecimalMinLongValidator.class);
			builtInValidators.add(DecimalMinShortValidator.class);			
		} else if(Digits.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(DigitsNumberValidator.class);
		} else if(Future.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(FutureDateValidator.class);
		} else if(Max.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(MaxBigDecimalValidator.class);
			builtInValidators.add(MaxBigIntegerValidator.class);
			builtInValidators.add(MaxByteValidator.class);
			builtInValidators.add(MaxDoubleValidator.class);
			builtInValidators.add(MaxFloatValidator.class);
			builtInValidators.add(MaxIntegerValidator.class);
			builtInValidators.add(MaxLongValidator.class);
			builtInValidators.add(MaxShortValidator.class);	
		} else if(Min.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(MinBigDecimalValidator.class);
			builtInValidators.add(MinBigIntegerValidator.class);
			builtInValidators.add(MinByteValidator.class);
			builtInValidators.add(MinDoubleValidator.class);
			builtInValidators.add(MinFloatValidator.class);
			builtInValidators.add(MinIntegerValidator.class);
			builtInValidators.add(MinLongValidator.class);
			builtInValidators.add(MinShortValidator.class);	
		} else if(NotNull.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(NotNullObjectValidator.class);
		} else if(Null.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(NullObjectValidator.class);
		} else if(Past.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(PastDateValdiator.class);
		} else if(Pattern.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(PatternStringValidator.class);
		} else if(Size.class.equals(constraintAnnotationClass)) {
			builtInValidators.add(SizeArrayValidator.class);
			builtInValidators.add(SizeCollectionValidator.class);
			builtInValidators.add(SizeMapValidator.class);
			builtInValidators.add(SizeStringValidator.class);
		} else {
			//do nothing?  not a built-in validator.
		}
		
		return builtInValidators;
	}
	
}
