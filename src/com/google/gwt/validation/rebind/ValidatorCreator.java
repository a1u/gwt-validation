package com.google.gwt.validation.rebind;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.validation.client.AbstractValidator;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.ValidationConfiguration;
import com.google.gwt.validation.client.interfaces.IInternalValidator;
import com.google.gwt.validation.client.interfaces.IValidator;

/**
 * Class that creates the validator for the given input class
 *
 * @author chris
 *
 */
public class ValidatorCreator {

	private static final String PROPERTY_MAP_VAR = "propertyMap";
	private TreeLogger logger;
	private GeneratorContext context;
	private String typeName;
	private TypeOracle typeOracle;

	private TypeStrategy typeStrategy;
	private String originalTypeName;

	private ValidatorCreator() {

	}

	/**
	 * Creates the generator from the parameters passed by the GWT compiler
	 *
	 * @param logger
	 * @param context
	 * @param typeName
	 */
	public ValidatorCreator(final TreeLogger logger, final GeneratorContext context, final String typeName, final TypeStrategy typeStrategy, final String originalTypeName) {

		//super
		this();

		//set others
		this.logger = logger;
		this.context = context;
		this.typeName = typeName;

		//get type oracle from context
		this.typeOracle = this.context.getTypeOracle();

		this.typeStrategy = typeStrategy;
		this.originalTypeName = originalTypeName;
	}

	private void generateValidationCheck(SourceWriter sw, List<ValidationPackage> validationPackageList, final String fullClass, boolean propertyMethodOrWholeObject) {
		for(final ValidationPackage vPack : validationPackageList) {

			//get implementing class name
			final String impl = vPack.getImplementingConstraint().getClass().getName();

			//create if string for the if block (includes propertyName and groups)
			String ifTest = "(propertyName == null || propertyName.equals(\"" + vPack.getItemName() + "\")) && (groups.size() == 0";

			//list all groups
			for(final String group : vPack.getGroups()) {
				ifTest += " || groups.contains(\"" + group + "\")";
			}
			//close if test
			ifTest += ")";

			//processed groups
			ifTest += " && (processedGroups.size() == 0 || (";

			//do the same for processed groups
			for(final String group : vPack.getGroups()) {
				ifTest += "!processedGroups.contains(\"" + group + "\") && ";
			}
			//close if test
			ifTest += "))";
			//replace ' && ))' with '))' because I can't think of a better
			//way to not have an && at the end of the series.
			ifTest = ifTest.replace(" && ))", "))");

			//create if block (used to break up scope and to test for propertyName / groups)
			sw.println("if(" + ifTest + ") {");
			sw.indent();

			//create property map output
			this.writeMapToCode(vPack.getValidationPropertyMap(), sw);

			//create validator instace from implementing validator
			sw.println(impl + " validator = new " + impl + "();");

			//initialize validator with property map
			sw.println("validator.initialize(propertyMap);");

			// either it is whole object or a subobject accessible through object's method
			String targetObject = (!propertyMethodOrWholeObject ? "object" : "object." + vPack.getMethod().getName() + "()");

			//do object method in if test
			sw.println("if(!validator.isValid(" + targetObject + ")) {");
			sw.indent();

			//create invalid constraint
			String message = vPack.getMessage();
			generateConstraintMessage(sw, message, vPack);
			sw.println(InvalidConstraint.class.getSimpleName() + "<" + fullClass + "> ivc = new " + InvalidConstraint.class.getSimpleName() + "<" + fullClass + ">(\"" + vPack.getItemName() + "\", message);");

			//add object
			sw.println("ivc.setInvalidObject(object);");

			//add value
			sw.println("ivc.setValue(" + targetObject + ");");

			//add to iCSet
			sw.println("iCSet.add(ivc);");

			//end if test for validator
			sw.outdent();
			sw.println("}");

			//end if block
			sw.outdent();
			sw.println("}");

		}
	}

	private void generateConstraintMessage(SourceWriter sw, String messageTemplate, ValidationPackage vPack) {
		Matcher m = Pattern.compile("\\{([^\\}]+)\\}").matcher(messageTemplate);
		if(m.matches()) {
			String key = m.group(1);
			key = transformDotNotationToMethodNotation(key);
			sw.println("javax.validation.ValidationMessages i18n = (javax.validation.ValidationMessages) GWT.create(javax.validation.ValidationMessages.class);");
			sw.println("String message = i18n." + key + "();");
			sw.println("message = " + ValidationConfiguration.class.getName() + ".getMessageInterpolator().interpolate(message, " + PROPERTY_MAP_VAR + ");");
		} else {
			sw.println("String message = " + PROPERTY_MAP_VAR + ".get(\"message\");");
		}
	}

	private String transformDotNotationToMethodNotation(String key) {
		return key.replaceAll("\\.", "_");
	}

	private void generateValidatorExceptionHandling(SourceWriter sw, String exceptionVariable) {
		sw.println("logError(" + exceptionVariable + ");");
	}

	/**
	 * Uses a sourcecode writer to write the implementation of the
	 * class's validator that was indicated at construction.  Returns
	 * the fully qualified class name of that implementation.
	 *
	 * @return
	 */
	public String createValidatorImplementation() {

		//string source
		String outputClassName = null;

		try {
			final JClassType originalClassType = this.typeOracle.getType(this.originalTypeName);

			//get class type
			final JClassType classType = this.typeOracle.getType(this.typeName);

			//write to string
			final SourceWriter sw = this.getSourceWriter(originalClassType, classType);

			//if sourcewriter is null, prematurely return the classname
			if(sw == null) {
              return originalClassType.getParameterizedQualifiedSourceName() + "Validator";
			}

			//fully qualified
			final String fullClass = classType.getQualifiedSourceName();

			try {

				//get the real class for the given class name
				final Class<?> inputClass = Class.forName(fullClass);

				//write the group mapping
				//protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(Class<?> inputClass) {
				sw.println("protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(Class<?> inputClass) {");
				sw.indent();
				sw.println("if(inputClass == null || Object.class.equals(inputClass)) return new HashMap<String, ArrayList<String>>();");
				sw.println("HashMap<String, ArrayList<String>> orderingMap = new HashMap<String, ArrayList<String>>();");

				//get list
				final HashMap<String, ArrayList<String>> groupSequenceMap = ValidationMetadataFactory.getGroupSequenceMap(inputClass);

				//for each thing thing create an ArrayList<String> of the order
				for(final String group : groupSequenceMap.keySet()) {
					//output (using scope trick)
					sw.println("if(true) {");
					sw.indent();

					//print line
					sw.println("ArrayList<String> groups = new ArrayList<String>();");

					//get group list
					final ArrayList<String> groupOrder = groupSequenceMap.get(group);

					//for each group add to the thing
					for(final String g : groupOrder) {
						//add to a group
						sw.println("groups.add(\"" + g + "\");");
					}

					//add groups to ordering map
					sw.println("orderingMap.put(\"" + group + "\",groups);");

					//close scope
					sw.outdent();
					sw.println("}");
				}

				//return
				sw.println("return orderingMap;");

				//close the HashMap
				sw.outdent();
				sw.println("}");

				//write "Set<InvalidConstraint> performValidation(T object, String propertyName, String... groups);" method
				sw.println("public Set<InvalidConstraint<" + fullClass + ">> performValidation(" + fullClass + " object, String propertyName, List<String> groups, Set<String> processedGroups, Set<String> processedObjects) {" );
				sw.indent();
				//create empty hashset of invalid constraints
				sw.println("HashSet<InvalidConstraint<" + fullClass + ">> iCSet = new HashSet<InvalidConstraint<" + fullClass + ">>();");

				//condition propertyName to be null if empty (simplify if test)
				sw.println("if(propertyName != null && (propertyName.trim().length() == 0)) {"); //there is no String.isEmpty() in jdk5
				sw.indent();
				sw.println("propertyName = null;");
				sw.outdent();
				sw.println("}");

				//groups listing
				sw.println("String grouplisting = \"\";");
				sw.println("for(String group : groups) {");
				sw.indent();
				sw.println("grouplisting += \":\" + group;");
				sw.outdent();
				sw.println("}");

				//get the validation package list for that class

				boolean isJsr303 = false;
				for(Field field : ValidationMetadataFactory.getAllFields(inputClass)) {
					Annotation[] annotations = field.getAnnotations();
					isJsr303 = isjsr303(annotations);
					if(isJsr303)
						break;
				}

				if(!isJsr303){
				for(Method method : ValidationMetadataFactory.getAllMethods(inputClass)){
					Annotation[] annotations = method.getAnnotations();
					isJsr303 = isjsr303(annotations);
					if(isJsr303)
						break;
				}
				}
				List<ValidationPackage> validationPackageList = null;
				if(!isJsr303){
					validationPackageList = ValidationMetadataFactory.getValidatorsForClass(inputClass);
				}
				else{
					validationPackageList = ValidationJsr303MetadataFactory.getValidatorsForClass(inputClass);
				}
				//do code output for each method
				generateValidationCheck(sw, validationPackageList, fullClass, true);

				//get list of packages for class level
				validationPackageList = ValidationMetadataFactory.getClassLevelValidatorsForClass(inputClass);

				//do code output for each class level validator
				generateValidationCheck(sw, validationPackageList, fullClass, false);

				//get @Valid package list
				final ArrayList<ValidationPackage> vpValidList = ValidationMetadataFactory.getValidAnnotedPackages(inputClass);

				//process annotations
				for(final ValidationPackage vPack : vpValidList) {

					try {
						//get return thingy
						final Class<?> returnType = vPack.getMethod().getReturnType();

						if(returnType != null) {

							//build if test and check that property name and object.method() are okay
							final String ifTest = "(propertyName == null || propertyName.equals(\"" + vPack.getItemName() + "\")) && object." + vPack.getMethod().getName() + "() != null";
							//put if around thing
							sw.println("if("+ifTest+") {");
							sw.indent();

							boolean validated = false;

							try {
								if(returnType.asSubclass(Object[].class) != null) {

									//get component type name
									final Type componentType = returnType.getComponentType();
									String typeName = componentType.toString();
									typeName = typeName.substring(typeName.indexOf(" "));

									//dont bork the whole thing on brittle failure
									sw.println("try {");
									sw.indent();

									//GWT.create() validator
									sw.println(IInternalValidator.class.getSimpleName() + "<" + typeName + "> subValidator = GWT.create(" + typeStrategy.getValidatorTypeName(typeName) + ".class);");

									//get the object array
									sw.println("" + typeName + "[] objectArray = object." + vPack.getMethod().getName() + "();");

									//go through the array
									sw.println("for(int it = 0; it < objectArray.length; ++it) {");
									sw.println(typeName + " innerObject = objectArray[it];");
									sw.indent();

									//create object identifier
									sw.println("String objIdent = System.identityHashCode(innerObject) + \":\" + System.identityHashCode(grouplisting);");

									//make sure it isn't part of the processed objects list
									sw.println("if(!processedObjects.contains(objIdent)) {");
									sw.indent();

									//add to the processed list
									sw.println("processedObjects.add(objIdent);");

									//validate the object
									sw.println("iCSet.addAll(this.unrollConstraintSet(object," +
											"\"" + vPack.getItemName() + "[\" + String.valueOf(it) + \"]\"" + //insert sequence no from collection in []
											",subValidator.performValidation(innerObject, null, groups, processedGroups, processedObjects)));");

									//done checking
									sw.outdent();
									sw.println("}");

									//end for loop
									sw.outdent();
									sw.println("}");

									sw.outdent();
									sw.println("} catch (Exception ex) {");
									sw.indent();
									sw.println("//error handling goes here");
									generateValidatorExceptionHandling(sw, "ex");
									sw.outdent();
									sw.println("}");

									//has been validated somehow, no other method needed
									validated = true;
								}
							} catch (final ClassCastException ccex) {

							}

							if(!validated) {

								try {
									final Class<?> collectionClass = returnType.asSubclass(Collection.class);

									if(collectionClass != null) {

										//get the type from the method declaration
										final Type typeToValidate = ((ParameterizedType)vPack.getMethod().getGenericReturnType()).getActualTypeArguments()[0];
										String typeName = typeToValidate.toString();
										typeName = typeName.substring(typeName.indexOf(" "));

										//dont bork the whole thing on brittle failure
										sw.println("try {");
										sw.indent();

										//GWT.create() validator
										sw.println(IInternalValidator.class.getSimpleName() + "<" + typeName + "> subValidator = GWT.create(" + typeStrategy.getValidatorTypeName(typeName) + ".class);");

										//get object instance
										sw.println("Collection<" + typeName + "> objectCollection = (Collection<" + typeName + ">) object." + vPack.getMethod().getName() + "();");

										//we need the counter
										sw.println("int it = 0;");

										//go through the collection
										sw.println("for(" + typeName + " innerObject : objectCollection) {");
										sw.indent();

										//create object identifier
										sw.println("String objIdent = System.identityHashCode(innerObject) + \":\" + System.identityHashCode(grouplisting);");

										//make sure it isn't part of the processed objects list
										sw.println("if(!processedObjects.contains(objIdent)) {");
										sw.indent();

										//add to the processed list
										sw.println("processedObjects.add(objIdent);");

										//validate the object
										sw.println("iCSet.addAll(this.unrollConstraintSet(object, "+
										        "\"" + vPack.getItemName() + "[\" + String.valueOf(it) + \"]\"" + //insert sequence no from collection in []
										        ", subValidator.performValidation(innerObject, null, groups, processedGroups, processedObjects)));");

										//increment counter
										sw.println("++it;");

										//done checking
										sw.outdent();
										sw.println("}");

										//end for loop
										sw.outdent();
										sw.println("}");

										sw.outdent();
										sw.println("} catch (Exception ex) {");
										sw.indent();
										sw.println("//error handling goes here");
										generateValidatorExceptionHandling(sw, "ex");
										sw.outdent();
										sw.println("}");

										//has been validated somehow, no other method needed
										validated = true;
									}
								} catch (final ClassCastException ccex) {

								}
							}

							if(!validated) {
								try {

									final Class<?> mapClass = returnType.asSubclass(Map.class);

									if(mapClass != null) {

										//get the types from the method declaration
										final Type keyType = ((ParameterizedType)vPack.getMethod().getGenericReturnType()).getActualTypeArguments()[0];
										String keyTypeName = keyType.toString();
										keyTypeName = keyTypeName.substring(keyTypeName.indexOf(" "));

										//get the type from the method declaration
										final Type typeToValidate = ((ParameterizedType)vPack.getMethod().getGenericReturnType()).getActualTypeArguments()[1];
										String typeName = typeToValidate.toString();
										typeName = typeName.substring(typeName.indexOf(" "));

										//dont bork the whole thing on brittle failure
										sw.println("try {");
										sw.indent();

										//GWT.create() validator
										sw.println(IInternalValidator.class.getSimpleName() + "<" + typeName + "> subValidator = GWT.create(" + typeStrategy.getValidatorTypeName(typeName) + ".class);");

										sw.println("Map<" + keyTypeName + "," + typeName + "> objectMap = (Map<" + keyTypeName + "," + typeName + ">) object." + vPack.getMethod().getName() + "();");

										//go through the map
										sw.println("for(" + keyTypeName + " mapKey : objectMap.keySet()) {");
										sw.println(typeName + " innerObject = objectMap.get(mapKey) ;");
										sw.indent();

										//create object identifier
                                        sw.println("String objIdent = System.identityHashCode(innerObject) + \":\" + System.identityHashCode(grouplisting);");

										//make sure it isn't part of the processed objects list
										sw.println("if(!processedObjects.contains(objIdent)) {");
										sw.indent();

										//add to the processed list
										sw.println("processedObjects.add(objIdent);");

										//validate the object
										sw.println("iCSet.addAll(this.unrollConstraintSet(object, " +
										        "\"" + vPack.getItemName() + "[\\\"\" + mapKey+ \"\\\"]\"" + //insert map key into []
										        ", subValidator.performValidation(innerObject, null, groups, processedGroups, processedObjects)));");

										//done checking
										sw.outdent();
										sw.println("}");

										//close for loop
										sw.outdent();
										sw.println("}");

										sw.outdent();
										sw.println("} catch (Exception ex) {");
										sw.indent();
										sw.println("//error handling goes here");
										generateValidatorExceptionHandling(sw, "ex");
										sw.outdent();
										sw.println("}");

										//has been validated somehow, no other method needed
										validated = true;
									}
								} catch (final ClassCastException ccex) {

								}

							}

							//if some other method has not been used...
							if(!validated) {

								//dont bork the whole thing on brittle failure
								sw.println("try {");
								sw.indent();

								//type name
								final String typeName = returnType.getCanonicalName();

								//GWT.create() validator
								sw.println(IInternalValidator.class.getSimpleName() + "<" + typeName + "> subValidator = GWT.create(" + typeStrategy.getValidatorTypeName(typeName) + ".class);");

								//get object
								sw.println("" + typeName + " innerObject = object." + vPack.getMethod().getName() + "();");

								//create object identifier
                                sw.println("String objIdent = System.identityHashCode(innerObject) + \":\" + System.identityHashCode(grouplisting);");

								//make sure it isn't part of the processed objects list
								sw.println("if(!processedObjects.contains(objIdent)) {");
								sw.indent();

								//add to the processed list
								sw.println("processedObjects.add(objIdent);");

								//validate
								sw.println("iCSet.addAll(this.unrollConstraintSet(object, \"" + vPack.getItemName() + "\", subValidator.performValidation(innerObject, null, groups, processedGroups, processedObjects)));");

								//close for loop
								sw.outdent();
								sw.println("}");

								//finish
								sw.outdent();
								sw.println("} catch (Exception ex) {");
								sw.indent();
								sw.println("//error handling goes here");
								generateValidatorExceptionHandling(sw, "ex");
								sw.outdent();
								sw.println("}");
							}

							//close if
							sw.outdent();
							sw.println("}");
						}

					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					}
				}

			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}

			//return thing
			sw.println("return iCSet;");
			//done with method
			sw.outdent();
			//close validate method
			sw.println("}");

			//commit to tree logger
			sw.commit(this.logger);

			//output class name
			outputClassName = originalClassType.getParameterizedQualifiedSourceName() + "Validator";

		} catch (final NotFoundException e) {
			this.logger.log(TreeLogger.ERROR, "Type " + this.typeName + " not found.");
		}

		//output class name
		return outputClassName;
	}

	private boolean isjsr303(Annotation[] annotations) {
		boolean isJsr303 = false;
		for (int i = 0; i < annotations.length; i++) {
			Annotation annotation = annotations[i];
			String pack = annotation.annotationType().getName();
			if(pack.startsWith("javax.validation")){
				isJsr303 = true;
				break;
			}
		}
		return isJsr303;
	}

	/**
	 * Create a source writer for classType + "Validator".
	 *
	 * @param classType
	 * @return
	 */
	private SourceWriter getSourceWriter(final JClassType classType, final JClassType beanClassType) {

		//get package
		final String packageName = classType.getPackage().getName();
		final String simpleName = classType.getSimpleSourceName() + "Validator";

		//get full name for package + validator
		//String fullName = packageName + "." + simpleName;

		//create validator that implements ivalidator for given typename
		final ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);

		//add the abstract validator method
		composer.setSuperclass(AbstractValidator.class.getCanonicalName() + "<" + beanClassType.getQualifiedSourceName() + ">");

		//add imports (other classes will be referenced by FULL class name)
		composer.addImport(List.class.getCanonicalName());
		composer.addImport(HashSet.class.getCanonicalName());
		composer.addImport(HashMap.class.getCanonicalName());
		composer.addImport(Set.class.getCanonicalName());
		composer.addImport(Map.class.getCanonicalName());
		composer.addImport(Collection.class.getCanonicalName());
		composer.addImport(ArrayList.class.getCanonicalName());
		composer.addImport(InvalidConstraint.class.getCanonicalName());
		composer.addImport(IValidator.class.getCanonicalName());
		composer.addImport(IInternalValidator.class.getCanonicalName());
		composer.addImport(GWT.class.getCanonicalName());

		//create print writer for the given source, if the print writer is null it either indicates
		//a problem with the filesystem or a that the class had already been created and does not
		//need to be created again.  in either case a null sourcewriter would be created and the
		//creation method would return just the name that should have been used so that it can
		//be loaded by the classloader.
		final PrintWriter printWriter = this.context.tryCreate(this.logger, packageName, simpleName);

		//set sourcewriter
		SourceWriter sw = null;

		//ensure a valid print writer
		if(printWriter != null) {
			//create sourcewriter
			sw = composer.createSourceWriter(this.context, printWriter);
			//this.logger.log(TreeLogger.INFO, "SourceWriter intialized from a non-null printwriter.");
		} else {
			//this.logger.log(TreeLogger.INFO, "PrintWriter was null so the validator class was most likely initialized already.");
		}

		//logging
		//this.logger.log(TreeLogger.INFO, "Validator dynamically created for " + fullName);

		//return sourcewriter
		return sw;
	}

	/**
	 * A helper method that writes a hashmap of properties for use in constructing
	 * the constraint for use in validation.
	 *
	 * @param propertyMap
	 * @param sw
	 */
	private void writeMapToCode(final Map<String, String> propertyMap, final SourceWriter sw) {

		//create empty object, regardless
		sw.println("HashMap<String,String> " + PROPERTY_MAP_VAR + " = new HashMap<String,String>();");

		if(propertyMap == null || propertyMap.size() == 0) return;

		for(final String key : propertyMap.keySet()) {
			//value
			String value = propertyMap.get(key);

			//put out propertykey
			//propertyMap.put(key, value);
			sw.println(PROPERTY_MAP_VAR + ".put(\"" + Generator.escape(key) + "\",\"" + Generator.escape(value) + "\");");
		}


	}

}
