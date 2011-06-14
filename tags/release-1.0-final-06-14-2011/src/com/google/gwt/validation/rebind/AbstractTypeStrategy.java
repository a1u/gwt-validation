/**
 * 
 */
package com.google.gwt.validation.rebind;

import com.google.gwt.core.ext.GeneratorContext;

/**
 * @author ladislav.gazo
 */
public abstract class AbstractTypeStrategy implements TypeStrategy {
	protected GeneratorContext context;
	
	public void setGeneratorContext(GeneratorContext context) {
		this.context = context;
	}
}
