# Roadmap #

## Release 2.3 ##
  * Implement XML Configuration
  * Pass 100% JSR-303 TCK tests

## Release 2.2 ##
  * Increase provable JSR-303 compatibility from the baseline established by the TCK against milestone 2.1 to 100% of non XML tests.

## Release 2.1 ##
  * Establish a baseline of JSR-303 compatibility with the [TCK](http://docs.jboss.org/hibernate/stable/beanvalidation/tck/reference/html_single/)
  * Fix defects from Release 2.0

## Release 2.0 ##
  * All features of 1.0 supported.
    * Class Level Validation
    * Message Interpolation
    * Group Sequence
    * Validation Implementation
  * The validateValue method fully implemented.
  * Generation of server side code through javassist or some other mechanism to reduce reliance on introspection and reflection and to increase similarity to client side process.
  * Code templates for client side generation to make modification easier
  * Built-in message interpolation and i8n support for messages

## Release 1.0 (deprecated) ##
  * Annotation based field/method validation.
  * Annotations from superclasses and implemented interfaces.
  * Group based selection of methods.
  * Validation on whole objects or single properties. (Client side and Server side)
  * Facilitates writing custom validators.
  * Class level validation.
  * Object graph validation through the @Valid annotation.
  * Groups fully working with "default" grouping and such.
  * @GroupSequence annotation.
  * InvalidConstraint is as JSR-303 compliant as it is likely to get