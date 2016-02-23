# Dependencies #

  * Validation libraries (javax.validation)
  * JUnit 4 (for testing)
  * GWT libraries (gwt-dev.jar, gwt-user.jar) `[`2.1 or greater`]`
  * [Freemarker](http://freemarker.sourceforge.net/) (for creating code from templates)
  * [Reflections](http://code.google.com/p/reflections/) `[`0.9.5`]` for class path scanning.
    * Simple Log4J, logback
    * DOM4J
    * Javassist
    * Guava (formerly google collections)

# Maven #

GWT Validation 2.0 supports a Maven pom.xml for building.  Simply run `mvn package` and Maven will compile and test the project for you and package it in target/gwt-validation-VERSION.jar.