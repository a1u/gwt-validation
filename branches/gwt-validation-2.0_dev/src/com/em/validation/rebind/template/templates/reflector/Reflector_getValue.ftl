		Object value = null;
	
		<#list properties as property><#if property_index &gt; 0> else </#if>if("${property.name}".equals(name)) {
			value = target.${property.accessor};				
		}</#list>
	
		return value;
