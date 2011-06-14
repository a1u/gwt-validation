package com.google.gwt.validation.rebind;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.validation.Constraint;
import javax.validation.groups.Default;

import com.google.gwt.validation.client.interfaces.IConstraint;

public class ValidationJsr303MetadataFactory {

	public static List<ValidationPackage> getValidatorsForClass(
			Class<?> inputClass) {

		// bail if problem with input class
		if (inputClass == null)
			return new ArrayList<ValidationPackage>();

		// holder hashmap for returnset
		ArrayList<ValidationPackage> validationPackageList = new ArrayList<ValidationPackage>();

		// map method names to (valid) annotations (those that contain a
		// ConstraintValidator)
		HashMap<Method, ArrayList<Annotation>> annotationMapping = new HashMap<Method, ArrayList<Annotation>>();

		// use arraylist to keep them in order
		ArrayList<Method> methodOrder = new ArrayList<Method>();

		// process fields (looking for annotations for subclasses as well)
		for (Field field : ValidationMetadataFactory.getAllFields(inputClass)) {

			// convert field to method
			Method fieldGetter = ValidationMetadataFactory
					.convertFieldToGetter(inputClass, field);

			// if the getter is not null, proceed with the rest of the method
			if (fieldGetter != null) {

				// get list of annotations on the field
				for (Annotation annotationOnField : field.getAnnotations()) {

					// annotation array
					Annotation[] annotationArray = new Annotation[] {};

					// annotation array (for determining if there are
					// validations withing the value of an annotation)
					try {
						Method valueOnAnnotation = annotationOnField
								.annotationType().getDeclaredMethod("value");
						if (valueOnAnnotation != null) {
							annotationArray = (Annotation[]) valueOnAnnotation
									.invoke(annotationOnField, new Object[] {});
						}
					} catch (SecurityException e) {
						// e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// e.printStackTrace();
					} catch (Exception ex) {
						// ex.printStackTrace();
					}

					// if the value method is an array of annotations
					// treat them as if they were all annotations on
					// the given method
					if (annotationArray.length > 0) {

						// process each annotation element in the array found on
						// .value()
						for (Annotation annotationElement : annotationArray) {

							// find a ConstraintValidator annotation on the
							// given annotation type (for the annotation on the
							// field)!
							Annotation checkTypeForConstraintValidator = annotationElement
									.annotationType().getAnnotation(
											Constraint.class);

							// get the arraylist of annotations for the method
							// name
							ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping
									.get(fieldGetter);
							if (listOfAnnotationsForGivenMethod == null)
								listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();

							// if the annotation is not null, add the annotation
							// on field to the annotation mapping for the given
							// method name
							if (checkTypeForConstraintValidator != null) {

								// get groups method
								listOfAnnotationsForGivenMethod
										.add(annotationElement);
							}

							// if the size of the list of annotations is > 0
							// save the arraylist back to the mapping
							if (listOfAnnotationsForGivenMethod != null
									&& listOfAnnotationsForGivenMethod.size() > 0) {
								annotationMapping.put(fieldGetter,
										listOfAnnotationsForGivenMethod);

								// add to ordering list
								if (!methodOrder.contains(fieldGetter))
									methodOrder.add(fieldGetter);
							}

						}

						// otherwise process as normal
					} else {

						// find a ConstraintValidator annotation on the given
						// annotation type (for the annotation on the field)!
						Annotation checkTypeForConstraintValidator = annotationOnField
								.annotationType().getAnnotation(
										Constraint.class);

						// get the arraylist of annotations for the method name
						ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping
								.get(fieldGetter);
						if (listOfAnnotationsForGivenMethod == null)
							listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();

						// if the annotation is not null, add the annotation on
						// field to the annotation mapping for the given method
						// name
						if (checkTypeForConstraintValidator != null) {

							// get groups method
							listOfAnnotationsForGivenMethod
									.add(annotationOnField);
						}

						// if the size of the list of annotations is > 0 save
						// the arraylist back to the mapping
						if (listOfAnnotationsForGivenMethod != null
								&& listOfAnnotationsForGivenMethod.size() > 0) {
							annotationMapping.put(fieldGetter,
									listOfAnnotationsForGivenMethod);

							// add to ordering list
							if (!methodOrder.contains(fieldGetter))
								methodOrder.add(fieldGetter);
						}

					}
				}

			} else {
				// skip field because it doesn't have a valid getter
			}

		}

		// process methods (looking for annotations on subclasses as well)
		for (Method method : ValidationMetadataFactory
				.getAllMethods(inputClass)) {

			// get integer modifier
			int methodModifiers = method.getModifiers();

			// methods must not already be in the mapping and must have 0
			// arguments (and must be accessible)
			if (!annotationMapping.containsKey(method)
					&& method.getParameterTypes().length == 0
					&& !Modifier.isPrivate(methodModifiers)
					&& !Modifier.isProtected(methodModifiers)) {

				// get list of annotations on the field
				for (Annotation annotationOnMethod : method.getAnnotations()) {

					// annotation array (for determining if there are
					// validations withing the value of an annotation)
					Annotation[] annotationArray = new Annotation[] {};

					// try and find a .value() on the annotation
					try {
						Method valueOnAnnotation = annotationOnMethod
								.annotationType().getDeclaredMethod("value");
						if (valueOnAnnotation != null) {
							annotationArray = (Annotation[]) valueOnAnnotation
									.invoke(annotationOnMethod, new Object[] {});
						}
					} catch (SecurityException e) {
						// e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// e.printStackTrace();
					} catch (Exception ex) {
						// ex.printStackTrace();
					}

					// if the value method is an array of annotations
					// treat them as if they were all annotations on
					// the given method
					if (annotationArray.length > 0) {

						// process each annotation element in the array found on
						// .value()
						for (Annotation annotatedElement : annotationArray) {

							// find a ConstraintValidator annotation on the
							// given annotation type (for the annotation on the
							// field)!
							Annotation checkTypeForConstraintValidator = annotatedElement
									.annotationType().getAnnotation(
											Constraint.class);

							// get the arraylist of annotations for the method
							// name
							ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping
									.get(method);
							if (listOfAnnotationsForGivenMethod == null)
								listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();

							// if the annotation is not null, add the annotation
							// on field to the annotation mapping for the given
							// method name
							if (checkTypeForConstraintValidator != null) {
								listOfAnnotationsForGivenMethod
										.add(annotatedElement);
							}

							// if the size of the list of annotations is > 0
							// save the arraylist back to the mapping
							if (listOfAnnotationsForGivenMethod != null
									&& listOfAnnotationsForGivenMethod.size() > 0) {
								annotationMapping.put(method,
										listOfAnnotationsForGivenMethod);

								// add to ordering list
								if (!methodOrder.contains(method))
									methodOrder.add(method);
							}
						}
						// otherwise continue processing the annotation as
						// normal
					} else {

						// find a ConstraintValidator annotation on the given
						// annotation type (for the annotation on the field)!
						Annotation checkTypeForConstraintValidator = annotationOnMethod
								.annotationType().getAnnotation(
										Constraint.class);

						// get the arraylist of annotations for the method name
						ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping
								.get(method);
						if (listOfAnnotationsForGivenMethod == null)
							listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();

						// if the annotation is not null, add the annotation on
						// field to the annotation mapping for the given method
						// name
						if (checkTypeForConstraintValidator != null) {
							listOfAnnotationsForGivenMethod
									.add(annotationOnMethod);
						}

						// if the size of the list of annotations is > 0 save
						// the arraylist back to the mapping
						if (listOfAnnotationsForGivenMethod != null
								&& listOfAnnotationsForGivenMethod.size() > 0) {
							annotationMapping.put(method,
									listOfAnnotationsForGivenMethod);

							// add to ordering list
							if (!methodOrder.contains(method))
								methodOrder.add(method);
						}
					}
				}
			}

		}

		Properties validatorsImpl =getValidatorsImpl();

		// build validation packages now that we have a list of the annotations
		// that belong to a given method or method/field pair
		for (Method method : methodOrder) {

			// double check method modifiers
			int modifiers = method.getModifiers();

			// make sure method is okay and good to go for a later call to
			// object.method() with 0 args and a return type != void/null
			if (annotationMapping.get(method).size() > 0
					&& !method.getReturnType().equals(void.class)
					&& !Modifier.isPrivate(modifiers)
					&& !Modifier.isProtected(modifiers)) {

				// get matching field, if one exists, for later use
				Field field = ValidationMetadataFactory
						.extrapolateFieldForGetter(inputClass, method);

				// get list for method
				ArrayList<Annotation> annotationListForMethod = annotationMapping
						.get(method);

				// protect from stupid
				if (annotationListForMethod != null
						&& annotationListForMethod.size() > 0) {

					// process annotations
					for (Annotation annotation : annotationListForMethod) {

						// get property map
						HashMap<String, String> propertyMap = new HashMap<String, String>();

						// get ConstraintValidator
						// ConstraintValidator<Annotation, ?> cv = null;
						IConstraint<Annotation> constraint = null;

						// get cv and constraint
						try {
							Class<?> validatorClass = null;
							String annotationName = annotation.annotationType()
									.getName();
							String validImpl = validatorsImpl.getProperty(annotationName);
							if (validImpl!=null) {
								try {
									validatorClass = Class.forName(validImpl);
								} catch (Exception e) {
								}
							}
							// cv = annotation.annotationType().getAnnotation(
							// Constraint.class);
							// Method valueMethod =
							// cv.annotationType().getMethod("value");
							// Class<?> validatorClass = (Class<?>)
							// valueMethod.invoke(cv);

							// suppress warnings because at this time we can be
							// reasonably sure that it is of
							// type IConstraint<Annotation> and that it does
							// extend object. I hate to rely on the "magic"
							// of trusting the compiler but for some of this
							// stuff it has become imperative.
							@SuppressWarnings("unchecked")
							IConstraint<Annotation> constraintFound = (IConstraint<Annotation>) validatorClass
									.getDeclaredConstructor().newInstance(
											new Object[] {});

							if (constraintFound != null) {
								constraint = constraintFound;
							}

						} catch (SecurityException e) {
							// e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// e.printStackTrace();
						} catch (IllegalAccessException e) {
							// e.printStackTrace();
						} catch (InvocationTargetException e) {
							// e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// e.printStackTrace();
						} catch (InstantiationException e) {
							// e.printStackTrace();
						}

						// group holder
						Class[] groups = new Class[] {};

						// get groups
						try {
							Method groupMethod = annotation.annotationType()
									.getDeclaredMethod("groups");
							Object invoke = groupMethod.invoke(annotation,
									new Object[] {});
							groups = (Class[]) groupMethod.invoke(annotation,
									new Object[] {});
						} catch (SecurityException e) {
							// e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// e.printStackTrace();
						} catch (IllegalAccessException e) {
							// e.printStackTrace();
						} catch (InvocationTargetException e) {
							// e.printStackTrace();
						}

						// process methods for properties
						for (Method annotationMethod : annotation
								.annotationType().getDeclaredMethods()) {

							// only get properties that aren't "groups"
							if (!annotationMethod.getName().equals("groups")) {

								// get value
								try {
									// get object for method invocation
									Object o = annotationMethod
											.invoke(annotation);

									// coerce to string (invoke toString through
									// tricky means)
									String value = "" + o;

									// set to propery map
									if (o != null) {
										propertyMap.put(annotationMethod
												.getName(), value);
									}

								} catch (IllegalArgumentException e) {
									// e.printStackTrace();
								} catch (IllegalAccessException e) {
									// e.printStackTrace();
								} catch (InvocationTargetException e) {
									// e.printStackTrace();
								}

							}
						}

						// once property map is finished
						String message = propertyMap.get("message");

						// we need to have a constraint for this to finish and
						// make the appropriate package
						if (constraint != null) {

							// initialize constraint with given annotation
							constraint.initialize(annotation);

							// build the validation package
							String[] groups2 = new String[groups.length];
							for (int i = 0; i < groups.length; i++) {
                                groups2[i] = extractGroupName(groups[i]);
							}
							ValidationPackage vPackage = new ValidationPackage(
									constraint, message, method, field,
									groups2, propertyMap);

							// a constraint was found, so add to validation list
							// thing
							validationPackageList.add(vPackage);
						}

						// all annotations processed
					}

					// that method had annotations
				}

				// that method was in the method map (paranoid check)
			}

			// all methods done and all packages created
		}

		return validationPackageList;

	}


    /** Extract group name, "default" group will be returned when Default.class occurs */
    private static String extractGroupName(final Class group) {
        if (Default.class.equals(group)) {
            return "default";
        } else {
            return group.getName();
        }
    }

    private static Properties getValidatorsImpl() {
		Properties properties = null;
		try {
			properties = new Properties();
			java.net.URL url = ClassLoader.getSystemResource("validator.properties");
			properties.load(url.openStream());
			return properties;
		} catch (IOException e) {
			return new Properties();
		}
	}
}
