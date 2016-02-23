# Blog #

Future updates will be [here, at this blog](http://breqs.blogspot.com/).

# Questions and Discussions #

Any questions, comments, or concerns should be directed to the Issues tab or to the [discussion group here](http://groups.google.com/group/gwt-validation).  (Not deep inside wiki pages.)

# Demo #

Finally, there is now a [demo available here](http://gwt-validation.googlecode.com/svn/demo/Gwt_validation_sample.html). You can find the source code for the demo [available here](http://code.google.com/p/gwt-validation/source/browse/#svn%2Ftrunk%2Fsample%2Fgwt-validation-sample).

There is now (as of [r231](https://code.google.com/p/gwt-validation/source/detail?r=231)) support for [internationalization and message interpolation](GWT_INTERNATIONALIZATION.md).  This can be shown in action in the sample.

[Default (english) locale](http://gwt-validation.googlecode.com/svn/demo/Gwt_validation_sample.html)

[French locale](http://gwt-validation.googlecode.com/svn/demo/Gwt_validation_sample.html?locale=fr)

[Spanish locale](http://gwt-validation.googlecode.com/svn/demo/Gwt_validation_sample.html?locale=es)

# Summary #

This project began as a response to [this issue](http://code.google.com/p/google-web-toolkit/issues/detail?id=343) in the GWT issue tracker.

Currently this project is written against Java 6 to provide the most current features.

This project supports the **2.1** branch of GWT and above.

The purpose is to provide an annotation based solution to both client side and server side validation.

As of milestone 2.0 we aim to be compliant with the JSR-303 specification **mandatory** sections.

Currently the library supports the following:
  * Annotation based field/method validation.
  * Annotations from superclasses and implemented interfaces.
  * @Group annotation for validation
  * Validation on whole objects or single properties. (Client side and Server side)
  * The validate a given field on a provided object with the validatValue() method (**new in 2.0**)
  * Facilitates writing custom validators.
  * Object graph validation through the @Valid annotation.
  * BeanDescriptor and PropertyDescriptor metadata is available (**new in 2.0**)
  * Message interpolation and i18n. (**new in 2.0**)
  * GroupSequence and implicit grouping (**[r253](https://code.google.com/p/gwt-validation/source/detail?r=253)**)
  * Class level validation (**[r272](https://code.google.com/p/gwt-validation/source/detail?r=272)**)

For further information on features see the [development roadmap](Roadmap.md).

# Using this project #

You will first need the [dependencies](Dependencies.md) needed to use this project on your classpath.  You will also need the gwt-validation version 2.0 jar on your classpath.

Then you will need to add the following to your Module.gwt.xml file in your gwt project.

```
<inherits name='com.em.validation.Validation' />
```

Then you can use the framework in a JSR-303 compliant manner, as shown below.

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

# Stats! #

&lt;wiki:gadget url="http://www.ohloh.net/p/43759/widgets/project\_basic\_stats.xml" height="220" border="1"/&gt;

&lt;wiki:gadget url="http://www.ohloh.net/p/43759/widgets/project\_cocomo.xml" height="240" border="0"/&gt;