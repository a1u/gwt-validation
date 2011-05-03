package ${targetPackage};

import java.util.Set;
import com.em.validation.client.reflector.Reflector;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import javax.validation.metadata.ConstraintDescriptor;

//we need the full directory where all the generated constraints are stored
import ${generatedConstraintPackage}.*;

<#list importList as import>
import ${import};
</#list>

public class ${concreteClassName} extends Reflector<${reflectionTargetName}> {
	<#include "Reflector_ClassBody.ftl">
}