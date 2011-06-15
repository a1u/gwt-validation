package com.em.validation.compiled.model.test;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import javax.validation.ConstraintValidatorFactory;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ConstraintValidatorFactoryGenerator;
import com.em.validation.rebind.generator.source.ReflectorFactoryGenerator;

public class CompiledValidationBaseTestClass implements ITestCase {

	private static IReflectorFactory reflectorFactory = null;
	private static ConstraintValidatorFactory validatorFactory = null;
	
	public CompiledValidationBaseTestClass() {
		DescriptorFactory.INSTANCE.setReflectorFactory(this.getReflectorFactory());
	}
	
	@Override
	public IReflectorFactory getReflectorFactory() {
		if(reflectorFactory == null) {
			Class<?> factoryClass = TestCompiler.INSTANCE.loadClass(ReflectorFactoryGenerator.INSTANCE.getReflectorFactoryDescriptors());
			try {
				CompiledValidationBaseTestClass.reflectorFactory = (IReflectorFactory)factoryClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
		return CompiledValidationBaseTestClass.reflectorFactory;
	}

	@Override
	public ConstraintValidatorFactory getConstraintValidationFactory() {
		if(validatorFactory == null) {
			Class<?> factoryClass = TestCompiler.INSTANCE.loadClass(ConstraintValidatorFactoryGenerator.INSTANCE.generateConstraintValidatorFactory());
			try {
				CompiledValidationBaseTestClass.validatorFactory = (ConstraintValidatorFactory)factoryClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
		return CompiledValidationBaseTestClass.validatorFactory;
	}	
	
}
