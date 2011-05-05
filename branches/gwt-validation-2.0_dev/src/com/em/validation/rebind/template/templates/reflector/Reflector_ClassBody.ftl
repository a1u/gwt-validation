	public ${concreteClassName}() {
		<#include "Reflector_Constructor.ftl">
	}

	public Object getValue(String name, ${reflectionTargetName} target){
		<#include "Reflector_getValue.ftl">
	}