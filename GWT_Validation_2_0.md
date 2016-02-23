# Introduction #

Even though the GWT SDK will include (some or all) JSR-303 compatibility with version 2.3 or 2.4 it still requires (from what knowledge is available) a non-client check of the file for constraint violations.  This is, at the very least, undesirable.


---


# Why? #

It is entirely possible to create a fully, or almost fully, complete JSR-303 validation solution, with GWT, that has the following features:

  * No special markers - naturally, fluently, use the JSR-303 (javax.validation) annotations without marker interfaces or annotations. (**COMPLETE**)
  * Metadata support - all of javax.valiation.metadata is supported. (**COMPLETE**)
  * All levels of validation (field, class, cascading). (**COMPLETE**)
  * Full group, message, and payload support. (**COMPLETE**)
  * Composed constraint support. (**COMPLETE**)
  * Full i18n support and message interpolation/parameterization support. (**COMPLETE**)


---


# JSR-303 Support Progress (80%) #

**Chapter 2: Constraint Definition (100%)**
> 2.1: Constraint Annotation (100%) <br />
> 2.2: Applying multiple constraints of the same type (100%) <br />
> 2.3: Constraint composition (100%) <br />
> 2.4: Constraint Validation Implementation (100%) <br />
> 2.5: ConstraintValidatorFactory (100%) <br />

**Chapter 3: Constraint declaration and validation process (75%)**
> 3.1:  Requirements on classes to be validated [with Introspector](implemented.md) (100%) <br />
> 3.2:  Constraint declaration (100%) <br />
> 3.3:  Inheritance (100%) <br />
> 3.4:  Group and group sequence (100%) <br />
> 3.5:  Validation Routine (100%) <br />
> 3.6:  Examples [in JUnit](implemented.md) (0%) <br />

**Chapter 4: Validation APIs (100%)**
> 4.1  Validator API (100%) <br />
> 4.2  ConstraintViolation (100%) <br />
> 4.3  Message Interpolation (100%) <br />
> 4.4  Bootstrapping (100%) <br />

**Chapter 5: Constraint metadata request APIs (100%)**
> 5.1: Validator (100%) <br />
> 5.2: ElementDescriptor (100%), ConstraintFinder (100%)
> 5.3: BeanDescriptor (100%) <br />
> 5.4: PropertyDescriptor (100%) <br />
> 5.5: ConstraintDescriptor (100%) <br />
> 5.6: Example [as JUnit test](implemented.md) (100%) <br />


---


# Instructions #

Please refer to the [dependencies](Dependencies.md) wiki page first for all the other dependencies you will need to run the validation framework.

You'll also need to add to your module xml file:
```
    <inherits name='com.em.validation.Validation' />
```

_Please note, the path to the validation module file has changed_

To use this in your code you will need to build a validation factory and use it to obtain Validator objects.  Any object that is **on your classpath** and **marked with a constraint** will have all the needed files generated.

Here is a simple example of a constrained object:
```
public class Person {

  @NotNull
  @Size(min=1,max=100) //could used composed NotEmpty and sacrifice length
  private String firstName = null;

  @NotNull
  @Size(min=1,max=100)//could used composed NotEmpty and sacrifice length
  private String lastName = null;

  public getFirstName() {
    return firstName;
  }

  public getLastName() {
    return lastName;
  } 

  /*
  ... snip setters and other methods ...
  */
}
```

Notice that, unlike gwt-validation 1.0, there is no marker interface.  The class will be found and have metadata generated at code generation time through the gwt deferred binding mechanism.

Now you need to get the validator object from the validation factory

```
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

/* ... snip ... */

//get validator factory using default bootstrap mechanism of the Validation library
ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
//get a validator instance
Validator validator = factory.getValidator();

//create new object
Person person = new Person();
person.setFirstName("Andrew");

//validate person object
Set<ConstraintViolation<Person>> violations = validator.validate(person);

//should be one violation from lastName being null
assert violations.size() == 1;
```

It's that simple.  No GWT.create() and no code differences between the client and the java code.  **None**.  Easy, fluent, and compliant.