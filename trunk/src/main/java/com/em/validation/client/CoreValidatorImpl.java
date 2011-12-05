package com.em.validation.client;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class CoreValidatorImpl implements Validator{
	
	private ConstraintValidatorFactory cvFactory = null;
	
	private TraversableResolver resolver = null;
	
	private MessageInterpolator interpolator = null;
	
	private ValidatorFactory factory = null; 
	
	public CoreValidatorImpl(ValidatorFactory factory) {
		this.factory = factory;
		this.cvFactory = this.factory.getConstraintValidatorFactory();
		this.resolver = this.factory.getTraversableResolver();
		this.interpolator = this.factory.getMessageInterpolator();
	}	

	@SuppressWarnings("unchecked")
	private <T> Set<ConstraintViolation<T>> validate(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, T object, Class<?>... groups) {
		//get the bean descriptor
		BeanDescriptor bean = this.getConstraintsForClass(object.getClass());
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//validate class level properties
		for(ConstraintDescriptor<?> descriptor : bean.findConstraints().declaredOn(ElementType.TYPE).unorderedAndMatchingGroups(groups).getConstraintDescriptors()) {
			violations.addAll(this.validateConstraint((Class<T>)object.getClass(), descriptor, object));
		}
		
		//validate each constrained property individually
		for(PropertyDescriptor property : bean.getConstrainedProperties()) {
			violations.addAll(this.validateProperty(validationCache, object, property.getPropertyName(), groups));
		}

		//update root objects for all violations
		for(ConstraintViolation<T> violation : violations) {
			if(violation instanceof ConstraintViolationImpl) {
				ConstraintViolationImpl<T> impl = (ConstraintViolationImpl<T>)violation;
				impl.setRootBean(object);
				impl.setRootBeanClass((Class<T>)object.getClass());
			}
		}
		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {		
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = this.createMap();
		//validate
		return this.validate(validationCache, object, groups);
	}

	@SuppressWarnings("unchecked")
	private <T> Set<ConstraintViolation<T>> validateProperty(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, T object, String propertyName, Class<?>... groups) {
		//get reflector to get property value
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector((Class<T>)object.getClass());
		
		if(!reflector.getPropertyNames().contains(propertyName)) {
			throw new IllegalArgumentException("Bean type " + object.getClass().getName() + " does not contain property " + propertyName);
		}
		
		Object value = reflector.getValue(propertyName, object);

		//create path for this level
		PathImpl path = new PathImpl();
		NodeImpl localNode = new NodeImpl();
		localNode.setName(propertyName);
		path.push(localNode);
		
		//reachable
		boolean reachable = false;
		
		try {
			reachable = this.resolver.isReachable(object, localNode, object.getClass(), this.getPath(new PathImpl(), propertyName), (ElementType)null);
		} catch (Exception ex) {
			throw new ValidationException("An exception occurred while checking the traversable resolver",ex);
		}
		
		//null violation set
		Set<ConstraintViolation<T>> violations = null;
		
		//only validate if reachable
		if(reachable) {
			violations = this.validateValue(validationCache,(Class<T>)object.getClass(), propertyName, value, groups);
			
			for(ConstraintViolation<T> violation : violations) {
				if(violation instanceof ConstraintViolationImpl) {
					//if no leaf bean has been set then set it at this level, this works because of the recursive nature of the algorithm.
					//the deepest properties get set first, therefore if the leaf is null then it has not been owned by a deeper property.
					if(violation.getLeafBean() == null) {
						ConstraintViolationImpl<T> impl = (ConstraintViolationImpl<T>)violation;
						impl.setLeafBean(object);
					}
				}
			}
		} else {
			violations = new HashSet<ConstraintViolation<T>>();
		}
		
		//validate property
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = this.createMap();
		//validate
		return this.validateProperty(validationCache, object, propertyName, groups);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Set<ConstraintViolation<T>> validateValue(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
		
		//get reflector to get property value
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(beanType);
		
		if(!reflector.getPropertyNames().contains(propertyName)) {
			throw new IllegalArgumentException("Bean type " + beanType.getName() + " does not contain property " + propertyName);
		}
		
		//create path for this level
		PathImpl path = new PathImpl();
		NodeImpl localNode = new NodeImpl();
		localNode.setName(propertyName);
		path.push(localNode);
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();

		//get the map of property names to the property values found on the given beanType
		Map<String,Map<Object,Set<ConstraintViolation<?>>>> propertyMap = validationCache.get(beanType);
		//create empty property->value map
		if(propertyMap == null) {
			propertyMap = new HashMap<String, Map<Object,Set<ConstraintViolation<?>>>>();
		}

		//get map of values that have already been seen to the constraint violations that were already provided
		Map<Object,Set<ConstraintViolation<?>>> valueMap = propertyMap.get(propertyName);
		//create empty value->constraint violation map
		if(valueMap == null) {
			valueMap = new HashMap<Object, Set<ConstraintViolation<?>>>();
		}
		
		//get the constraints already seen for that value
		Set<ConstraintViolation<?>> cachedViolations = valueMap.get(value);
		
		if(cachedViolations == null) {
			//get the bean descriptor and the property descriptor
			BeanDescriptor bean = this.getConstraintsForClass(beanType);
			
			PropertyDescriptor property = bean.getConstraintsForProperty(propertyName);
			
			//get constraints for beanType.propertyName
			Set<ConstraintDescriptor<?>> constraints  = property.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();

			//if no constraints are found using those groups, then we must take additional measures,
			//namely checking to see if it is a group interface instead of a plain group (see JSR-303
			//section 3.4.4. "Implicit Grouping")
			if(constraints == null || constraints.isEmpty()) {
				for(Class<?> group : groups) {
					//get bean descriptor for class
					BeanDescriptor groupBean = this.getConstraintsForClass(group);
					//get property if it exists on the bean
					PropertyDescriptor groupProperty = groupBean.getConstraintsForProperty(propertyName);
					//if the bean property exists and is constrained, add the constraints
					if(groupProperty != null && groupProperty.hasConstraints()) {
						constraints.addAll(groupProperty.getConstraintDescriptors());
					} 
				}
			}
			
			//loop and validate
			for(ConstraintDescriptor<?> descriptor : constraints) {
				violations.addAll(this.validateConstraint(beanType, descriptor, value));
			}

			//update maps back into cache
			Set<ConstraintViolation<?>> insertSet = new LinkedHashSet<ConstraintViolation<?>>();
			insertSet.addAll(violations);
			for(ConstraintViolation<?> violation : insertSet) {
				if(violation instanceof ConstraintViolationImpl) {
					PathImpl violationPath = new PathImpl();
					for(Node node : path) {
						violationPath.push(node);
					}
					for(Node node : ((ConstraintViolationImpl<?>)violation).getPropertyPath()) {
						violationPath.push(node);
					}
					((ConstraintViolationImpl<?>)violation).setPropertyPath(violationPath);
				}
			}			
			valueMap.put(value, insertSet);
			propertyMap.put(propertyName, valueMap);
			validationCache.put(beanType, propertyMap);
			
			if(value != null) {

				//if the property is cascaded (and the value is not null), follow the rabbit hole so that it can pick up the earlier cascades
				if(property.isCascaded()) {
					
					//perform validation on each map value
					if(value instanceof Object[]) {
					
						int index = 0;
						
						for(Object subValue : ((Object[])value)) {
							boolean cascadable = false;
							try {
								cascadable = this.resolver.isCascadable(value, localNode, beanType, this.getPath(path, index), (ElementType)null);
							} catch (Exception ex) {
								throw new ValidationException("An exception occurred while checking the traversable resolver",ex);
							}
							
							if(cascadable) {
								Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();
								cascadeViolations.addAll(this.validate(validationCache, subValue, groups));	
								violations.addAll(this.convertViolations(cascadeViolations, valueMap, beanType, path, index, null));
							}						
							index++;
						}
					//perform validation on each iterable object
					} else if (value instanceof Iterable) {
					
						//sets have no index
						int index = (value instanceof Set) ? -1 : 0;
											
						for(Object subValue : ((Iterable)value)) {
							boolean cascadable = false;
							try {
								cascadable = this.resolver.isCascadable(value, localNode, beanType, this.getPath(path, index), (ElementType)null);
							} catch (Exception ex) {
								throw new ValidationException("An exception occurred while checking the traversable resolver",ex);
							}
							
							if(cascadable) {
								Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();
								cascadeViolations.addAll(this.validate(validationCache, subValue, groups));	
								violations.addAll(this.convertViolations(cascadeViolations, valueMap, beanType, path, index, null));
							}
							
							//sets have no index
							if(!(value instanceof Set)) {
								index++;
							}
						}
					//perform validation on a map type object
					} else 	if(value instanceof Map) {
						for(Object key : ((Map)value).keySet()) {
							boolean cascadable = false;
							try {
								cascadable = this.resolver.isCascadable(value, localNode, beanType, this.getPath(path, key), (ElementType)null);
							} catch (Exception ex) {
								throw new ValidationException("An exception occurred while checking the traversable resolver",ex);
							}
							
							if(cascadable) {
								Object subValue = ((Map)value).get(key);
								Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();	
								cascadeViolations.addAll(this.validate(validationCache, subValue, groups));	
								violations.addAll(this.convertViolations(cascadeViolations, valueMap, beanType, path, null, key));
							}
						}						
					//perform validation on the object itself
					} else {
						boolean cascadable = false;
						
						try {
							cascadable = this.resolver.isCascadable(value, localNode, beanType, this.getPath(path, propertyName), (ElementType)null);
						} catch (Exception ex) {
							throw new ValidationException("An exception occurred while checking the traversable resolver",ex);
						}
						
						if(cascadable) {
							Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();
							cascadeViolations.addAll(this.validate(validationCache, value, groups));	
							violations.addAll(this.convertViolations(cascadeViolations, valueMap, beanType, path, null, null));
						}
					}
					
				}
			}
		} else {
			for(ConstraintViolation<?> constraintViolation : cachedViolations) {
				violations.add((ConstraintViolation<T>)constraintViolation);
			}
		}
		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = this.createMap();
		//call value with pre-provided (empty) map cache
		return this.validateValue(validationCache, beanType, propertyName, value, groups);
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		if(clazz == null) {
			throw new IllegalArgumentException("Cannot get constraints for a null class.");
		}
		
		return DescriptorFactory.INSTANCE.getBeanDescriptor(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(CoreValidatorImpl.class.equals(type)) {
			return (T) new CoreValidatorImpl(this.factory);
		}
		throw new ValidationException("This API only supports unwrapping " + CoreValidatorImpl.class.getName() + " (and not " + type.getName() + ").");
	}
	
	@SuppressWarnings("unchecked")
	private <T> Set<ConstraintViolation<T>> validateConstraint(Class<T> beanType, ConstraintDescriptor<?> descriptor, Object value) {
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//only do this part of the validation if there is at least one constraint validator
		if(!descriptor.getConstraintValidatorClasses().isEmpty()) {

			//ambiguous validator resolution results in a failure as well (jsr303-tck)
			//java.lang.AssertionError: The test should have failed due to ambiguous validator resolution.
			// org.hibernate.jsr303.tck.tests.constraints.validatorresolution.ValidatorResolutionTest.testAmbiguousValidatorResolution(ValidatorResolutionTest.java:247)
			if(descriptor.getConstraintValidatorClasses().size() > 1) {
				//throw new UnexpectedTypeException("More than one validator was resolved for " + descriptor.getAnnotation().annotationType().getName() + " on " + beanType.getName());
			}
			
			//get the validator
			Class<? extends ConstraintValidator<? extends Annotation, T>> validatorClass = (Class<? extends ConstraintValidator<? extends Annotation, T>>) descriptor.getConstraintValidatorClasses().get(0);

			//if a validator class was found, create the validator and begin validation
			if(validatorClass != null) {
				try {
					//default to true
					boolean result = true;
					
					@SuppressWarnings("rawtypes")
					ConstraintValidator cValidator = this.cvFactory.getInstance(validatorClass);

					//throw an exception if a null validator is found (JSR-303 TCK "testValidationexceptionIsThrowInCaseFactoryReturnsNull")
					if(cValidator == null) {
						throw new ValidationException("Validation factory returned a null constraint validator for the class " + validatorClass.getName() + ".");
					}
					
					//otherwise, continue validation
					try {
						cValidator.initialize(descriptor.getAnnotation());
					} catch (ClassCastException ccex) {
						throw new ConstraintDefinitionException("Could not cast constraint definition.",ccex);
					} catch (Exception ex) {
						throw new ValidationException("An exception occured while intializing constraint description.", ex);
					}
					ConstraintValidatorContextImpl validatorContext = new ConstraintValidatorContextImpl(descriptor);
					result = cValidator.isValid(value, validatorContext);

					//when the result is false, create a constraint violation for the local element
					if(!result) {
						//craft single constraint violation on this node
						ConstraintViolationImpl<T> localViolation = new ConstraintViolationImpl<T>();
						
						//create context
						MessageInterpolatorContextImpl context = new MessageInterpolatorContextImpl();
						context.setConstraintDescriptor(descriptor);
						context.setValidatedValue(value);
						
						String messageKey = (String)descriptor.getAttributes().get("message");
						String interpolatedMessage = this.interpolator.interpolate(messageKey, context);
						
						//set violation
						localViolation.setConstraintDescriptor(descriptor);
						localViolation.setMessage(interpolatedMessage);
						localViolation.setMessageTemplate(messageKey);
						localViolation.setInvalidValue(value);
						localViolation.setRootBeanClass(beanType);
						
						//add local violation
						violations.add(localViolation);
					}
	
				} catch (ValidationException vex) {
					//todo: log that the validator cValidator was skipped b/c of a validation error
				}			
			}
		} else if(descriptor.getComposingConstraints().isEmpty()) {
			//empty constraint validation, fail
			throw new UnexpectedTypeException("No validator was found for " + descriptor.getAnnotation().annotationType().getName() + " with type " + beanType.getName());
		}
		
		//composed violation
		Set<ConstraintViolation<T>> composedViolations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//do composing constraints
		if(!descriptor.getComposingConstraints().isEmpty()) {
			//get the results for all of the composed constraints
			for(ConstraintDescriptor<?> composedOf : descriptor.getComposingConstraints()) {
				Set<ConstraintViolation<T>> local = this.validateConstraint(beanType, composedOf, value);
				composedViolations.addAll(local);
			}
			
			//if this violation isn't being reported as a single violation, report all of the composing violations
			if(!descriptor.isReportAsSingleViolation()) {
				violations.addAll(composedViolations);
			} else {
				//otherwise we need to report one single violation, for all the violations we have
				if(composedViolations.size() > 0) {
					ConstraintViolationImpl<T> localViolation = new ConstraintViolationImpl<T>();

					//create context
					MessageInterpolatorContextImpl context = new MessageInterpolatorContextImpl();
					context.setConstraintDescriptor(descriptor);
					context.setValidatedValue(value);
					
					String messageKey = (String)descriptor.getAttributes().get("message");
					String interpolatedMessage = this.interpolator.interpolate(messageKey, context);
					
					//set violation
					localViolation.setConstraintDescriptor(descriptor);
					localViolation.setMessage(interpolatedMessage);
					localViolation.setMessageTemplate(messageKey);
					localViolation.setInvalidValue(value);
					localViolation.setRootBeanClass(beanType);
				
					//add single constraint violation to local violations list
					violations.add(localViolation);
				}
			}
		}
		
		return violations;
	}
	
	/**
	 * Converts violations at one level (child) to another (higher, parent) level
	 * 
	 * @param inputConstriants
	 * @param value
	 * @param type
	 * @param path
	 * @return
	 */
	private <T> Set<ConstraintViolation<T>> convertViolations(Set<ConstraintViolation<Object>> inputConstriants, Object value, Class<T> type, PathImpl path, Integer index, Object key) {
		
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		
		for(ConstraintViolation<?> violation : inputConstriants) {
			ConstraintViolationImpl<T> convertedViolation = new ConstraintViolationImpl<T>();
			
			//convert violations
			convertedViolation.setConstraintDescriptor(violation.getConstraintDescriptor());
			convertedViolation.setMessage(violation.getMessage());
			convertedViolation.setPropertyPath(violation.getPropertyPath());
			convertedViolation.setMessageTemplate(violation.getMessageTemplate());
			convertedViolation.setInvalidValue(violation.getInvalidValue());
			convertedViolation.setLeafBean(value);
			convertedViolation.setRootBeanClass(type);
			
			//append to path
			PathImpl cascadePath = this.getPath(path, null, index, key);
		
			//push violation paths on after property name
			for(Node node : violation.getPropertyPath()) {
				cascadePath.push(node);
			}							
			
			//set as converted path
			convertedViolation.setPropertyPath(cascadePath);
			
			violations.add(convertedViolation);
		}
		
		return violations;
	}
	
	private PathImpl getPath(Path rootPath, String propertyPath, Integer index, Object key) {
		//create new path from old path
		PathImpl cascadePath = new PathImpl();
		for(Node node : rootPath) {
			cascadePath.push(node);
		}
		
		//create a node that represents the object map or array index
		NodeImpl cascadeNode = null;
		if(index != null || key != null) {
			cascadeNode = new NodeImpl();
			if(index != null) {
				cascadeNode.setIndex(index);
			} else if(key != null) {
				cascadeNode.setKey(key);
			}
			cascadeNode.setInIterable(true);
		//otherwise one that represents property access
		} else if(propertyPath != null && !propertyPath.isEmpty()) {
			cascadeNode = new NodeImpl();
			cascadeNode.setName(propertyPath);
		}
		
		//part of issue #43 was that it would happily stick in a node that came from a null source
		//since this code was substituted all over the core validator all of the null filtering is
		//done here and NOT in the source anymore.
		if(cascadeNode != null) {
			cascadePath.push(cascadeNode);
		}
		
		return cascadePath;
	}
	
	private PathImpl getPath(Path rootPath, String propertyPath) {
		return this.getPath(rootPath, propertyPath, null, null);
	}
	
	private PathImpl getPath(Path rootPath, Integer index) {
		return this.getPath(rootPath, null, index, null);
	}
	
	private PathImpl getPath(Path rootPath, Object key) {
		return this.getPath(rootPath, null, null, key);
	}
	
	/**
	 * Standard method for creating an empty validation cache
	 * 
	 * @return
	 */
	private Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> createMap() {
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> newValidationCache = new HashMap<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>>();
		return newValidationCache;
	}


}
