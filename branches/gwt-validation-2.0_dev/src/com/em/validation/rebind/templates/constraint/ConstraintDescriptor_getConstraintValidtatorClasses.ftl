		List<Class<? extends ConstraintValidator<${targetAnnotation}, ?>>> validatedBy = new ArrayList<Class<? extends ConstraintValidator<${targetAnnotation}, ?>>>();
		<#list validatedBy as validator>
		validatedBy.add(validator);
		</#list>	
		return validatedBy;