package com.google.gwt.validation.rebind;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.validation.client.ConstraintValidator;
import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.GroupSequences;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IConstraint;

public class ValidationMetadataFactory {

	/**
	 * Returns a list of validators that apply to each method of a given class, that are given for the valid list of
	 * groups and that are in order by group ordering as annotated in the class.  All field validators are converted
	 * to the appropriate getter, if one exists.  Only 0 argument methods are considered for constraint validation.
	 * 
	 * 
	 * @param className
	 * @return
	 */
	public static ArrayList<ValidationPackage> getValidatorsForClass(Class<?> inputClass) {
		
		//bail if problem with input class
		if(inputClass == null) return new ArrayList<ValidationPackage>();
		
		//holder hashmap for returnset
		ArrayList<ValidationPackage> validationPackageList = new ArrayList<ValidationPackage>();
		
		//map method names to (valid) annotations (those that contain a ConstraintValidator)
		HashMap<Method, ArrayList<Annotation>> annotationMapping = new HashMap<Method, ArrayList<Annotation>>();
		
		//use arraylist to keep them in order
		ArrayList<Method> methodOrder = new ArrayList<Method>();
		
		//process fields (looking for annotations for subclasses as well)
		for(Field field : ValidationMetadataFactory.getAllFields(inputClass)) {
			
			//convert field to method
			Method fieldGetter = ValidationMetadataFactory.convertFieldToGetter(inputClass, field);
			
			//if the getter is not null, proceed with the rest of the method
			if(fieldGetter != null) {
				
				//get list of annotations on the field
				for(Annotation annotationOnField : field.getAnnotations()) {
					
					//annotation array
					Annotation[] annotationArray = new Annotation[]{};
					
					//annotation array (for determining if there are validations withing the value of an annotation)
					try {
						Method valueOnAnnotation = annotationOnField.annotationType().getDeclaredMethod("value");
						if(valueOnAnnotation != null) {
							annotationArray = (Annotation[])valueOnAnnotation.invoke(annotationOnField, new Object[]{});
						}					
					} catch (SecurityException e) {
						//e.printStackTrace();
					} catch (NoSuchMethodException e) {
						//e.printStackTrace();
					} catch (Exception ex) {
						//ex.printStackTrace();
					}
					
					//if the value method is an array of annotations
					//treat them as if they were all annotations on
					//the given method
					if(annotationArray.length > 0) {
						
						//process each annotation element in the array found on .value()
						for(Annotation annotationElement : annotationArray) {

							//find a ConstraintValidator annotation on the given annotation type (for the annotation on the field)!
							Annotation checkTypeForConstraintValidator = annotationElement.annotationType().getAnnotation(ConstraintValidator.class);
												
							//get the arraylist of annotations for the method name
							ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(fieldGetter);
							if(listOfAnnotationsForGivenMethod == null) listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
							
							//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
							if(checkTypeForConstraintValidator != null) {
			
								//get groups method
								listOfAnnotationsForGivenMethod.add(annotationElement);
							}
							
							//if the size of the list of annotations is > 0 save the arraylist back to the mapping
							if(listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
								annotationMapping.put(fieldGetter, listOfAnnotationsForGivenMethod);
								
								//add to ordering list
								if(!methodOrder.contains(fieldGetter)) methodOrder.add(fieldGetter);
							}							
							
						}
						
					//otherwise process as normal
 					} else {
				
						//find a ConstraintValidator annotation on the given annotation type (for the annotation on the field)!
						Annotation checkTypeForConstraintValidator = annotationOnField.annotationType().getAnnotation(ConstraintValidator.class);
											
						//get the arraylist of annotations for the method name
						ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(fieldGetter);
						if(listOfAnnotationsForGivenMethod == null) listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
						
						//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
						if(checkTypeForConstraintValidator != null) {
		
							//get groups method
							listOfAnnotationsForGivenMethod.add(annotationOnField);
						}
						
						//if the size of the list of annotations is > 0 save the arraylist back to the mapping
						if(listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
							annotationMapping.put(fieldGetter, listOfAnnotationsForGivenMethod);
							
							//add to ordering list
							if(!methodOrder.contains(fieldGetter)) methodOrder.add(fieldGetter);
						}
						
 					}
				}				
				
			} else {
				//skip field because it doesn't have a valid getter
			}
			
		}
		
		//process methods (looking for annotations on subclasses as well)
		for(Method method : ValidationMetadataFactory.getAllMethods(inputClass)) {
			
			//get integer modifier
			int methodModifiers = method.getModifiers();
			
			//methods must not already be in the mapping and must have 0 arguments (and must be accessible)
			if(!annotationMapping.containsKey(method) && method.getParameterTypes().length == 0 && !Modifier.isPrivate(methodModifiers) && !Modifier.isProtected(methodModifiers)) {
				
				//get list of annotations on the field
				for(Annotation annotationOnMethod : method.getAnnotations()) {
					
					//annotation array (for determining if there are validations withing the value of an annotation)
					Annotation[] annotationArray = new Annotation[]{};
					
					//try and find a .value() on the annotation
					try {
						Method valueOnAnnotation = annotationOnMethod.annotationType().getDeclaredMethod("value");
						if(valueOnAnnotation != null) {
							annotationArray = (Annotation[])valueOnAnnotation.invoke(annotationOnMethod, new Object[]{});
						}					
					} catch (SecurityException e) {
						//e.printStackTrace();
					} catch (NoSuchMethodException e) {
						//e.printStackTrace();
					} catch (Exception ex) {
						//ex.printStackTrace();
					}
					
					//if the value method is an array of annotations
					//treat them as if they were all annotations on
					//the given method
					if(annotationArray.length > 0) {
					
						//process each annotation element in the array found on .value()
						for(Annotation annotatedElement : annotationArray) {
							
							//find a ConstraintValidator annotation on the given annotation type (for the annotation on the field)!
							Annotation checkTypeForConstraintValidator = annotatedElement.annotationType().getAnnotation(ConstraintValidator.class);
							
							//get the arraylist of annotations for the method name
							ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(method);
							if(listOfAnnotationsForGivenMethod == null) listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
							
							//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
							if(checkTypeForConstraintValidator != null) {
								listOfAnnotationsForGivenMethod.add(annotatedElement);
							}
							
							//if the size of the list of annotations is > 0 save the arraylist back to the mapping
							if(listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
								annotationMapping.put(method, listOfAnnotationsForGivenMethod);
								
								//add to ordering list
								if(!methodOrder.contains(method)) methodOrder.add(method);
							}							
						}
					//otherwise continue processing the annotation as normal	
					} else {
						
						//find a ConstraintValidator annotation on the given annotation type (for the annotation on the field)!
						Annotation checkTypeForConstraintValidator = annotationOnMethod.annotationType().getAnnotation(ConstraintValidator.class);
						
						//get the arraylist of annotations for the method name
						ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(method);
						if(listOfAnnotationsForGivenMethod == null) listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
						
						//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
						if(checkTypeForConstraintValidator != null) {
							listOfAnnotationsForGivenMethod.add(annotationOnMethod);
						}
						
						//if the size of the list of annotations is > 0 save the arraylist back to the mapping
						if(listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
							annotationMapping.put(method, listOfAnnotationsForGivenMethod);
							
							//add to ordering list
							if(!methodOrder.contains(method)) methodOrder.add(method);
						}
					}
				}				
			}
			
		}
		
		//build validation packages now that we have a list of the annotations that belong to a given method or method/field pair
		for(Method method : methodOrder) {
		
			//double check method modifiers
			int modifiers = method.getModifiers();
			
			//make sure method is okay and good to go for a later call to object.method() with 0 args and a return type != void/null
			if(annotationMapping.get(method).size() > 0 && !method.getReturnType().equals(void.class) && !Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers)) {
			
				//get matching field, if one exists, for later use
				Field field = ValidationMetadataFactory.extrapolateFieldForGetter(inputClass, method);
				
				//get list for method
				ArrayList<Annotation> annotationListForMethod = annotationMapping.get(method);
				
				//protect from stupid
				if(annotationListForMethod != null && annotationListForMethod.size() > 0) {
					
					//process annotations
					for(Annotation annotation : annotationListForMethod) {
						
						//get property map
						HashMap<String, String> propertyMap = new HashMap<String, String>();
						
						//get ConstraintValidator
						ConstraintValidator cv = null;
						IConstraint<Annotation> constraint = null;
						
						//get cv and constraint
						try {
							cv = annotation.annotationType().getAnnotation(ConstraintValidator.class);
							Method valueMethod = cv.annotationType().getMethod("value");
							Class<?> validatorClass = (Class<?>) valueMethod.invoke(cv);
							
							//suppress warnings because at this time we can be reasonably sure that it is of
							//type IConstraint<Annotation> and that it does extend object.  I hate to rely on the "magic"
							//of trusting the compiler but for some of this stuff it has become imperative.
							@SuppressWarnings("unchecked") 
							IConstraint<Annotation> constraintFound = (IConstraint<Annotation>)validatorClass.getDeclaredConstructor().newInstance(new Object[]{});
							
							if(constraintFound != null) {
								constraint = constraintFound;
							}
							
						} catch (SecurityException e) {
							//e.printStackTrace();
						} catch (IllegalArgumentException e) {
							//e.printStackTrace();
						} catch (IllegalAccessException e) {
							//e.printStackTrace();
						} catch (InvocationTargetException e) {
							//e.printStackTrace();
						} catch (NoSuchMethodException e) {
							//e.printStackTrace();
						} catch (InstantiationException e) {
							//e.printStackTrace();
						}
						
						//group holder
						String[] groups = new String[]{};
						
						//get groups
						try {
							Method groupMethod = annotation.annotationType().getDeclaredMethod("groups");
							groups = (String[]) groupMethod.invoke(annotation, new Object[]{});							
						} catch (SecurityException e) {
							//e.printStackTrace();
						} catch (NoSuchMethodException e) {
							//e.printStackTrace();
						} catch (IllegalArgumentException e) {
							//e.printStackTrace();
						} catch (IllegalAccessException e) {
							//e.printStackTrace();
						} catch (InvocationTargetException e) {
							//e.printStackTrace();
						}
						
						//process methods for properties
						for(Method annotationMethod : annotation.annotationType().getDeclaredMethods()) {
							
							//only get properties that aren't "groups"
							if(!annotationMethod.getName().equals("groups")) {
								
								//get value
								try {
									//get object for method invocation
									Object o = annotationMethod.invoke(annotation);
									
									//coerce to string (invoke toString through tricky means)
									String value = "" + o;
			
									//set to propery map
									if(o != null) {
										propertyMap.put(annotationMethod.getName(), value);
									}
									
								} catch (IllegalArgumentException e) {
									//e.printStackTrace();
								} catch (IllegalAccessException e) {
									//e.printStackTrace();
								} catch (InvocationTargetException e) {
									//e.printStackTrace();
								}
								
							}
						}
						
						//once property map is finished
						String message = propertyMap.get("message");
						
						//we need to have a constraint for this to finish and make the appropriate package
						if(constraint != null) {
							
							//initialize constraint with given annotation
							constraint.initialize(annotation);
						
							//build the validation package
							ValidationPackage vPackage = new ValidationPackage(constraint, message, method, field, groups, propertyMap);
							
							//a constraint was found, so add to validation list thing
							validationPackageList.add(vPackage);
						}
						
						//all annotations processed
					}
					
					//that method had annotations
				}
				
				//that method was in the method map (paranoid check) 
			}
			
			//all methods done and all packages created
		}		
		
		return validationPackageList;
	}

	public static ArrayList<ValidationPackage> getClassLevelValidatorsForClass(Class<?> inputClass) {
		
		//breakout if input is invalid
		if(inputClass == null || inputClass.equals(Object.class)) return new ArrayList<ValidationPackage>();
		
		//create empty array list
		ArrayList<ValidationPackage> classValidationPackageList = new ArrayList<ValidationPackage>();
		
		//get annotations
		Annotation[] classLevelAnnotationlist = inputClass.getAnnotations();
		
		//process annotations
		for(Annotation classAnnotation : classLevelAnnotationlist) {
			
			IConstraint<Annotation> constraint = null;
			
			try {
				
				ConstraintValidator cv = classAnnotation.annotationType().getAnnotation(ConstraintValidator.class);
				Method valueMethod;
				valueMethod = cv.annotationType().getMethod("value");

				Class<?> validatorClass = (Class<?>) valueMethod.invoke(cv);
				
				//suppress warnings because at this time we can be reasonably sure that it is of
				//type IConstraint<Annotation> and that it does extend object.  I hate to rely on the "magic"
				//of trusting the compiler but for some of this stuff it has become imperative.
				@SuppressWarnings("unchecked")
				IConstraint<Annotation> constraintFound = (IConstraint<Annotation>)validatorClass.getDeclaredConstructor().newInstance(new Object[]{});
				
				if(constraintFound != null) {
					constraint = constraintFound;
				}	
		
			} catch (SecurityException e) {
				//e.printStackTrace();
			} catch (NoSuchMethodException e) {
				//e.printStackTrace();
			} catch (IllegalArgumentException e) {
				//e.printStackTrace();
			} catch (IllegalAccessException e) {
				//e.printStackTrace();
			} catch (InvocationTargetException e) {
				//e.printStackTrace();
			} catch (InstantiationException e) {
				//e.printStackTrace();
			} catch (NullPointerException npe) {
				//not a proper constraint annotation for what we're looking for
			}
		
			//if a constraint was found, build validation package
			if(constraint != null) {
				
				//build property map
				HashMap<String, String> propertyMap = new HashMap<String, String>();
				
				//initialize constraint
				constraint.initialize(classAnnotation);
				
				//group holder
				String[] groups = new String[]{};
				
				//get groups
				try {
					Method groupMethod = classAnnotation.annotationType().getDeclaredMethod("groups");
					groups = (String[]) groupMethod.invoke(classAnnotation, new Object[]{});							
				} catch (SecurityException e) {
					//e.printStackTrace();
				} catch (NoSuchMethodException e) {
					//e.printStackTrace();
				} catch (IllegalArgumentException e) {
					//e.printStackTrace();
				} catch (IllegalAccessException e) {
					//e.printStackTrace();
				} catch (InvocationTargetException e) {
					//e.printStackTrace();
				}
				
				//process methods for properties
				for(Method annotationMethod : classAnnotation.annotationType().getDeclaredMethods()) {
					
					//only get properties that aren't "groups"
					if(!annotationMethod.getName().equals("groups")) {
						
						//get value
						try {
							//get object for method invocation
							Object o = annotationMethod.invoke(classAnnotation);
							
							//coerce to string (invoke toString through tricky means)
							String value = "" + o;
	
							//set to propery map
							if(o != null) {
								propertyMap.put(annotationMethod.getName(), value);
							}
							
						} catch (IllegalArgumentException e) {
							//e.printStackTrace();
						} catch (IllegalAccessException e) {
							//e.printStackTrace();
						} catch (InvocationTargetException e) {
							//e.printStackTrace();
						}
						
					}
				}
				
				//once property map is finished
				String message = propertyMap.get("message");				
				
				//build validation package
				ValidationPackage vp = new ValidationPackage(constraint, message, null, null, groups, propertyMap);

				//add validation package to list
				classValidationPackageList.add(vp);
			}
			
		}
		
		//do the same for superclass
		Class<?> superClass = inputClass.getSuperclass();
		if(superClass != null) {
			classValidationPackageList.addAll(ValidationMetadataFactory.getClassLevelValidatorsForClass(superClass));
		}
		
		//do the same for each interface
		for(Class<?> interfaceClass : inputClass.getInterfaces()) {
			classValidationPackageList.addAll(ValidationMetadataFactory.getClassLevelValidatorsForClass(interfaceClass));
		}
		
		//return list
		return classValidationPackageList;
	}
	
	public static ArrayList<ValidationPackage> getValidAnnotedPackages(Class<?> inputClass) {
		//create empty array
		ArrayList<ValidationPackage> validAnnotationPackageList = new ArrayList<ValidationPackage>();
		
		//if the input class is null
		if(inputClass == null) return validAnnotationPackageList;

		//use arraylist to keep them in order
		ArrayList<Method> methodOrder = new ArrayList<Method>();
		
		//process fields (looking for annotations for subclasses as well)
		for(Field field : ValidationMetadataFactory.getAllFields(inputClass)) {
			
			//convert field to method
			Method fieldGetter = ValidationMetadataFactory.convertFieldToGetter(inputClass, field);
			
			//if the getter is not null, proceed with the rest of the method
			if(fieldGetter != null) {
				
			    //find a @Valid annotation on the given annotation type (for the annotation on the field)!
				Annotation checkForValid = field.getAnnotation(Valid.class);
			
				//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
				if(checkForValid != null) {

					//get groups method
					if(!methodOrder.contains(fieldGetter)) methodOrder.add(fieldGetter);
				}
			
				
			} else {
				//skip field because it doesn't have a valid getter
			}
			
		}
		
		//process methods (looking for annotations on subclasses as well)
		for(Method method : ValidationMetadataFactory.getAllMethods(inputClass)) {
			
			//get integer modifier
			int methodModifiers = method.getModifiers();
			
			//methods must not already be in the mapping and must have 0 arguments (and must be accessible)
			if(!methodOrder.contains(method) && method.getParameterTypes().length == 0 && !Modifier.isPrivate(methodModifiers) && !Modifier.isProtected(methodModifiers)) {
				
			    //find a @Valid annotation on the given annotation type (for the annotation on the field)!
				Annotation checkForValid = method.getAnnotation(Valid.class);
			
				//if the annotation is not null, add the annotation on field to the annotation mapping for the given method name
				if(checkForValid != null) {

					//get groups method
					if(!methodOrder.contains(method)) methodOrder.add(method);
				}			
			}
			
		}
		
		//build validation packages now that we have a list of the annotations that belong to a given method or method/field pair
		for(Method method : methodOrder) {
		
			//double check method modifiers
			int modifiers = method.getModifiers();
			
			//make sure method is okay and good to go for a later call to object.method() with 0 args and a return type != void/null
			if(!method.getReturnType().equals(void.class) && !Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers)) {
			
				//get matching field, if one exists, for later use
				Field field = ValidationMetadataFactory.extrapolateFieldForGetter(inputClass, method);
				
				//group holder
				String[] groups = new String[]{};
				
				//get valid annotation
				Annotation annotation = method.getAnnotation(Valid.class);
				if(annotation == null) {
					annotation = field.getAnnotation(Valid.class);
				}
				
				if(annotation != null) {
				
					//get groups
					try {
						Method groupMethod = annotation.annotationType().getDeclaredMethod("groups");
						groups = (String[]) groupMethod.invoke(annotation, new Object[]{});							
					} catch (SecurityException e) {
						//e.printStackTrace();
					} catch (NoSuchMethodException e) {
						//e.printStackTrace();
					} catch (IllegalArgumentException e) {
						//e.printStackTrace();
					} catch (IllegalAccessException e) {
						//e.printStackTrace();
					} catch (InvocationTargetException e) {
						//e.printStackTrace();
					}
						

					//create package and add
					ValidationPackage vp = new ValidationPackage(null, "{message.cascadingvalidator}", method, field, groups, new HashMap<String, String>());
					
					//add validation package to list
					validAnnotationPackageList.add(vp);
				}
 
			}
			
			//all methods done and all packages created
		}		
		
		//return this list of annoated packages
		return validAnnotationPackageList;
	}
	
	
	/**
	 * Attempts to find a getter for the given field
	 * 
	 * @param inputClass
	 * @param inputField
	 * @return
	 */
	private static Method convertFieldToGetter(Class<?> inputClass, Field inputField) {
		
		//get field name
		String name = inputField.getName();
		
		//chop off first char
		String getterName = name.substring(1);
 
		//get first char
		String first = name.charAt(0) + "";
		
		//first is uppercase
		if(first.equals(first.toUpperCase())) {
			getterName = first.toLowerCase() + getterName;
		} else {
			getterName = first.toUpperCase() + getterName;
		}
		
		//add get
		getterName = "get" + getterName;
		
		//method!
		Method returnMethod = null;
		
		//attempt to get method
		returnMethod = ValidationMetadataFactory.getMethodByName(inputClass, getterName);
		
		//if returned parameter size isn't 0, then reset to null
		if(returnMethod != null && returnMethod.getParameterTypes().length != 0) {
			returnMethod = null;
		}
		
		//check for boolean "is"
		if(returnMethod == null) {
			
			getterName = getterName.replaceFirst("get", "is");
		
			//attempt to get method
			returnMethod = ValidationMetadataFactory.getMethodByName(inputClass, getterName);
			
			//if returned parameter size isn't 0, then reset to null
			if(returnMethod != null && returnMethod.getParameterTypes().length != 0) {
				returnMethod = null;
			}

		}
		
		//return found getter
		return returnMethod;
	}
	
	/**
	 * Attempts to find the Field for a given Method
	 * 
	 * @param inputClass
	 * @param inputMethod
	 * @return
	 */
	private static Field extrapolateFieldForGetter(Class<?> inputClass, Method inputMethod) {
		
		//get field name
		String name = "";
		if(inputMethod.getName().indexOf("get") == 0) {
			name = inputMethod.getName().replaceFirst("get", "");
		} else if(inputMethod.getName().indexOf("is") == 0) {
			name = inputMethod.getName().replaceFirst("is", "");
		}
		
		//return null if the name is borked
		if(name.length() == 0 || name.trim().length() == 0) return null;
		
		//chop off first char
		String fieldName = name.substring(1);
 
		//get first char
		String first = name.charAt(0) + "";
		
		//first is uppercase
		if(first.equals(first.toUpperCase())) {
			fieldName = first.toLowerCase() + fieldName;
		} else {
			fieldName = first.toUpperCase() + fieldName;
		}
		
		//field holder
		Field returnField = ValidationMetadataFactory.getFieldByName(inputClass, fieldName);		
		
		//return field
		return returnField;
	}
	
	/**
	 * Gets all of the fields (both declared and inherited) for
	 * the given class
	 * 
	 * @param inputClass
	 * @return
	 */
	private static HashSet<Field> getAllFields(Class<?> inputClass) {
	
		//condition to quit recursive search
		if(inputClass == null || inputClass.equals(Object.class)) return new HashSet<Field>();
		
		//fields
		Field[] closedField = inputClass.getFields();
		Field[] openField = inputClass.getDeclaredFields();
		
		//hashset
		HashSet<Field> fieldSet = new HashSet<Field>();
		
		for(Field f : openField) {
			fieldSet.add(f);
		}
		
		
		for(Field f : closedField) {
			fieldSet.add(f);
		}

		//add superclass to fieldset
		fieldSet.addAll(ValidationMetadataFactory.getAllFields(inputClass.getSuperclass()));		
	
		//add all interface classes to fieldset
		for(Class<?> interfaceClass : inputClass.getInterfaces()) {
			fieldSet.addAll(ValidationMetadataFactory.getAllFields(interfaceClass));
		}		
		
		return fieldSet;
	}
	
	/**
	 * Gets all methods (declared and inherited) for the given class
	 * 
	 * @param inputClass
	 * @return
	 */
	private static HashSet<Method> getAllMethods(Class<?> inputClass) {
		
		//condition to quit recursive search
		if(inputClass == null || inputClass.equals(Object.class)) return new HashSet<Method>();
		
		//fields
		Method[] closedMethod = inputClass.getMethods();
		Method[] openMethod = inputClass.getDeclaredMethods();
		
		//hashset
		HashSet<Method> methodSet = new HashSet<Method>();
		
		for(Method m : openMethod) {
			methodSet.add(m);
		}
		
		for(Method m : closedMethod) {
			methodSet.add(m);
		}
		
		//add superclass to method set
		methodSet.addAll(ValidationMetadataFactory.getAllMethods(inputClass.getSuperclass()));		
	
		//add all interface classes to fieldset
		for(Class<?> interfaceClass : inputClass.getInterfaces()) {
			methodSet.addAll(ValidationMetadataFactory.getAllMethods(interfaceClass));
		}
		
		return methodSet;
	}
	
	/**
	 * Searches all methods (inherited and declared) for the method name, returns the first one it finds
	 * 
	 * @param inputClass
	 * @param name
	 * @return
	 */
	private static Method getMethodByName(Class<?> inputClass, String name) {
		
		HashSet<Method> methodList = ValidationMetadataFactory.getAllMethods(inputClass);
		
		Method returnMethod = null;
		
		for(Method m : methodList) {
			if(m.getName().equals(name)) {
				returnMethod = m;
				break;
			}
		}
		
		return returnMethod;
		
	}
	
	/**
	 * Searches all fields (inherited and declared) for the field name, returns the first one
	 * it finds
	 * 
	 * @param inputClass
	 * @param name
	 * @return
	 */
	private static Field getFieldByName(Class<?> inputClass, String name) {
		
		HashSet<Field> fieldList = ValidationMetadataFactory.getAllFields(inputClass);
		
		Field returnField = null;
		
		for(Field f : fieldList) {
			if(f.getName().equals(name)) {
				returnField = f;
				break;
			}
		}
		
		return returnField;
		
	}
	
	/**
	 * Returns a <code>java.util.Hashset</code> that contains the String key of the sequence name along
	 * with a list of all of the groups in that sequence in the proper order.  
	 * 
	 * @param inputClass the class to check for GroupSequences
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> getGroupSequenceMap(Class<?> inputClass) {
		
		if(inputClass == null || Object.class.equals(inputClass)) return new HashMap<String, ArrayList<String>>();
		
		//create empty HashMap
		HashMap<String, ArrayList<String>> orderingMap = new HashMap<String, ArrayList<String>>();
		
		//create annotations array list
		ArrayList<GroupSequence> gsList = new ArrayList<GroupSequence>();
		
		//by unifying all groupsequences groups into a single array list
		//with the groupsequence annoation we can process them all from
		//the arraylist by using the same logic
		GroupSequences groupSequences = inputClass.getAnnotation(GroupSequences.class);
		if(groupSequences != null) {
			
			for(GroupSequence gs : groupSequences.value()) {
				gsList.add(gs);
			}
			
		}
		
		//get groupsequence annotation
		GroupSequence groupSequence = inputClass.getAnnotation(GroupSequence.class);
		if(groupSequence != null) {
			gsList.add(groupSequence);
		}
		
		//process list
		for(GroupSequence gs : gsList) {
			
			if(gs.sequence() != null) {
				
				//create new array list for storing group names from the sequence
				ArrayList<String> groupNames = new ArrayList<String>();
				
				//add to group names
				for(String name : gs.sequence()) {
					groupNames.add(name);
				}
				
				//if no group names found add "default". no idea /why/ someone would do
				//this but we need to keep this consistant across the application
				if(groupNames.size() == 0) groupNames.add("default");
				
				//add to map
				if(gs.name() != null && !(gs.name().trim().length() == 0)) {
					//get gs name
					String groupSequenceName = gs.name().trim();
					
					//ensure does not already exist in group ordering
					if(!orderingMap.containsKey(groupSequenceName)) {
						orderingMap.put(groupSequenceName, groupNames);
					}
				}
			}
			
		}
		
		//process superclass and interfaces
		HashMap<String, ArrayList<String>> superGroups = ValidationMetadataFactory.getGroupSequenceMap(inputClass.getSuperclass());
		for(Class<?> interfaceClass : inputClass.getInterfaces()) {
			superGroups.putAll(ValidationMetadataFactory.getGroupSequenceMap(interfaceClass));
		}
		
		//merge without duplicates so that the rule that only one groupsequence with a name can be defined
		for(String superGroupName : superGroups.keySet()) {
			
			//only add if key is not in ordering map
			if(!orderingMap.containsKey(superGroupName) && superGroups.get(superGroupName) != null) {
				orderingMap.put(superGroupName, superGroups.get(superGroupName));
			}
		}
		
		//return ordering map
		return orderingMap;
	}
}
