# Description #

The GWTVF implements i18n as described in the JSR-303 specification (v1.0 final).  The full details can be found in section 4.3.

# Files #

The JSR-303 specification specifies that the resource bundle pattern `/ValidationMessages.properties` should be used to specify the resources.  The following files would specify a default locale, an English locale, a French locale, and a Spanish locale.

```
/ValidationMessages.properties
/ValidationMessages_en.properties
/ValidationMessages_fr.properties
```

The `/` location refers to the root of the classpath.

These files behave as a [ResourceBundle](http://download.oracle.com/javase/6/docs/api/java/util/ResourceBundle.html) would behave.

# Usage #

In order to use the i18n features you must do the following:

  1. Create the `/ValidationMessages` property files
  1. Add the appropriate locales to your `<module`>.gwt.xml
  1. Use the validation framework as normal

# Examples #

In your project you will need to add the supported locales to your module xml file as shown below (step #1 above).

```
<?xml version="1.0" encoding="UTF-8"?>
<module rename-to='gwt_validation_sample'>
  <!-- Inherit the core Web Toolkit stuff.                        -->
  <inherits name='com.google.gwt.user.User'/>

  <!-- Inherit the default GWT style sheet.  You can change       -->
  <!-- the theme of your GWT application by uncommenting          -->
  <!-- any one of the following lines.                            -->
  <inherits name='com.google.gwt.user.theme.clean.Clean'/>
  <!-- <inherits name='com.google.gwt.user.theme.standard.Standard'/> -->
  <!-- <inherits name='com.google.gwt.user.theme.chrome.Chrome'/> -->
  <!-- <inherits name='com.google.gwt.user.theme.dark.Dark'/>     -->

  <!-- Other module inherits                                      -->
  <inherits name='com.em.validation.Validation' />

  <!-- Specify the app entry point class.                         -->
  <entry-point class='com.em.validation.sample.client.Gwt_validation_sample'/>
 
  <!-- English language, independent of country -->
  <extend-property name="locale" values="en"/>

  <!-- French language, independent of country -->
  <extend-property name="locale" values="fr"/>
  
  <!-- Spanish language, independent of country -->
  <extend-property name="locale" values="es"/>

  <!-- Default language (English) -->
  <set-property-fallback name="locale" value="en"/>

  <!-- Specify the paths for translatable code                    -->
  <source path='client'/>
  <source path='shared'/>

</module>
```

The important lines are:

```
  <!-- English language, independent of country -->
  <extend-property name="locale" values="en"/>

  <!-- French language, independent of country -->
  <extend-property name="locale" values="fr"/>
  
  <!-- Spanish language, independent of country -->
  <extend-property name="locale" values="es"/>

  <!-- Default language (English) -->
  <set-property-fallback name="locale" value="en"/>
```

You will also need to place one properties file per locale in your path, as show below.  If a property file is missing, the defaults will be used for that locale.

/ValidationMessages.propreties (default)

```
javax.validation.constraints.Size.message=The property must be between {min} and {max}
javax.validation.constraints.NotNull.message=The property must not be null
```

/ValidationMessages\_fr.properties (french)

```
javax.validation.constraints.Size.message=La propriété doit être comprise entre {min} et {max}
javax.validation.constraints.NotNull.message=La propriété ne doit pas être nulle
```

/ValidationMessages\_es.properties (spanish)

```
javax.validation.constraints.Size.message=La propiedad debe estar entre {min} y {max}
javax.validation.constraints.NotNull.message=La propiedad no debe ser nulo
```

At this point you can just work as normal with the framework and the current locale will be detected and used to provide the translated values.