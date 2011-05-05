package ${targetPackage};

//generic imports
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;
import com.em.validation.client.metadata.AbstractConstraintDescriptor;

//the target constraint annotation
import ${annotationImportName};

public class ${generatedName} extends AbstractConstraintDescriptor<${targetAnnotation}> {
	<#include "ConstraintDescriptor_ClassBody.ftl">
}