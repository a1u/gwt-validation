# Strategy of validation generator #

In a discussion following one of the wiki articles there was reasonable request to execute validation generation based not on the bean class name but an interface. From previous experience in complex situations/projects it is better not to bind bean class with stuff like validators.

Validation generator allows you to choose or even implement your own strategy using TypeStrategy class. Default strategy is still the one using bean class but from now on you can use IValidator interface to define bean validator interface. Currently there are two strategies:
  * BeanTypeNameStrategy
    * usage: `GWT.create(Person.class)`
    * generates PersonValidator which validates Person bean
  * InterfaceTypeNameStrategy
    * usage is based on the interface you choose but let's expect it is existing `IValidator<T>`
      * `public interface PersonValidator extends IValidator<Person>`
      * usage: `GWT.create(PersonValidator.class)`
    * it is important that the interface has one generics type - type of the bean

Available generators:
  * Default generator **ValidatorGenerator** uses BeanTypeNameStrategy
  * **InterfaceDrivenValidatorGenerator** uses InterfaceTypeNameStrategy with `IValidator<T>` interface

## Custom interface generator ##

If you want to have custom interface you can implement your own generator:

```
public class MyInterfaceDrivenValidatorGenerator extends ValidatorGenerator {
	public MyInterfaceDrivenValidatorGenerator() {
		typeStrategy = new InterfaceTypeNameStrategy(MyInterface.class, "MyInterface");
	}
}

public interface MyInterface<T> {}
```