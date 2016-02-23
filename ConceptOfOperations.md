# Introduction #

The Google Web Toolkit is a Framework for compiling specific portions of the Java API to JavaScript along with widgets to visually display the information.  The widgets work as HTML and JavaScript code but are expressed as Java code during development.

The GWT Validation Framework is intended to provide methods for the validation of model objects on the Client side with mirroring validation on the Server side.

# Overview #

The GWT Validation Framework (GWTVF) consists of several components.

### External API ###

The external component is defined by the JSR-303 specification.  This is the API for accessing the validation mechanisms either through the server side or client side code.

### Annotations ###

The annotations are created by the developers to express constraints (as defined by the JSR-303 specification) on a given class or methods and fields within that class.  These annotations must conform to the JSR-303 specification to be recognized by the GWTVF.  The GWTVF includes constraints suggested in the JSR-303 but more can be written or expressed by end-users.

### Client Side Validation ###

The validation code is dynamically written at compile time to accommodate the validations used by the end user in the code that they have written.  The validators that are created by this process are available to the end user as Validators specified by the JSR-303 and operate to validate objects of the types that were annotated at design time.

### Server Side Validation ###

Server side validation is accomplished by using a similar method to client side validation but that is directed through introspection and reflection.  This code is more flexible but can only be used in the JVM.

### Products of the Validation Process ###

As a product of the Validation process an Invalid Constraint (see [InvalidConstraint](InvalidConstraint.md)) is produced.

# Objectives #
  * Provide a framework for validating Java Objects both on the client and server side.
  * Provide built-in constraints for common validation tasks.
  * Provide an easy metaphor for form validation through events.
  * Provide a framework for writing custom validators.
  * Provide the ability to define a hierarchy or sequence of validations.

# Operation #

First the user defines a class with some constraint annotations.  These can be from the built in constraints or from ones defined by the user.

```
public class Person {
  
  @Size(min=1)
  @NotNull
  private String lastName;

  @NotNull
  @Size(min=3)
  private String firstName;

  public String getLastName() { return this.lastName; }
  public String getFirstName() { return this.firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  @Pattern(regexp="(.*), (.*)")
  public String getFullName() {
     return this.lastName + ", " + this.firstName;
  }
}
```

At this point the user can begin to use the validation framework.  First they will need a ValidatorFactory which can be easily built with the default method.
```
ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
```

Once the factory has been created, individual validators can be built.  In this case, for the person factory.  Because of the "magic" of deferred binding in version 2.0, the same code is used on the client and server side.

```
Person p = new Person();
Validator validator = factory.getValidator();
Set<ConstraintViolation<Person>> violations = validator.validate(p);
```

At the end of this example the `violations` set would contain a list of all of the violations that occurred during the validation on Person.

The process of validation occurs as specified in the JSR-303 standard and basically occurs in the following order:
  * Property constraings
  * Class level constraints
  * Object Graph (which consists of the following)
    * Lists
    * Collections
    * Maps
    * Child Objects

Once validation is complete a Set of [InvalidConstraint](InvalidConstraint.md)s is returned.  All of the places that the object failed constraints are contained in that set.  In the above example two [InvalidConstraint](InvalidConstraint.md)s would be returned.  One for each of the fields being @NotNull.

For a further example
```
Person p = new Person();
p.setFirstName("Bob");
p.setLastName("Ti");
```

Validating the p instance of Person would cause one invalid constraint for @Length on the property lastName.  This is because a minimum length of 3 is specified by the @Size constraint on the lastName property.

Once the project linking against the GWTVF is compiled and the validators for each used validation are created the validation can be performed on the client or server side based on the validations.