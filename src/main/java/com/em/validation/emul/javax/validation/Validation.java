// $Id: Validation.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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
package javax.validation;

import javax.validation.Configuration;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import javax.validation.spi.ValidationProvider;

import com.em.validation.client.ConfigurationImpl;
import com.em.validation.client.ValidatorFactoryImpl;

/**
 * This class is the entry point for Bean Validation. There are three ways
 * to bootstrap it:
 * <ul>
 * <li>
 * The easiest approach is to build the default <code>ValidatorFactory</code>.
 * <pre>{@code ValidatorFactory factory = Validation.buildDefaultValidatorFactory();}</pre>
 * In this case, the default validation provider resolver
 * will be used to locate available providers.
 * The chosen provider is defined as followed:
 * <ul>
 * <li>if the XML configuration defines a provider, this provider is used</li>
 * <li>if the XML configuration does not define a provider or if no XML configuration
 * is present the first provider returned by the
 * <code>ValidationProviderResolver</code> instance is used.</li>
 * </ul>
 * </li>
 * <li>
 * The second bootstrap approach allows to choose a custom
 * <code>ValidationProviderResolver</code>. The chosen
 * <code>ValidationProvider</code> is then determined in the same way
 * as in the default bootstrapping case (see above).
 * <pre>{@code
 * Configuration<?> configuration = Validation
 *    .byDefaultProvider()
 *    .providerResolver( new MyResolverStrategy() )
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();}
 * </pre>
 * </li>
 * <li>
 * The third approach allows you to specify explicitly and in
 * a type safe fashion the expected provider.
 * <p/>
 * Optionally you can choose a custom <code>ValidationProviderResolver</code>.
 * <pre>{@code
 * ACMEConfiguration configuration = Validation
 *    .byProvider(ACMEProvider.class)
 *    .providerResolver( new MyResolverStrategy() )  // optionally set the provider resolver
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();}
 * </pre>
 * </li>
 * </ul>
 * Note:<br/>
 * <ul>
 * <li>
 * The <code>ValidatorFactory</code> object built by the bootstrap process should be cached
 * and shared amongst <code>Validator</code> consumers.
 * </li>
 * <li>
 * This class is thread-safe.
 * </li>
 * </ul>
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public class Validation {

	/**
	 * Build and return a <code>ValidatorFactory</code> instance based on the
	 * default Bean Validation provider and following the XML configuration.
	 * <p/>
	 * The provider list is resolved using the default validation provider resolver
	 * logic.
	 * <p/> The code is semantically equivalent to
	 * <code>Validation.byDefaultProvider().configure().buildValidatorFactory()</code>
	 *
	 * @return <code>ValidatorFactory</code> instance.
	 *
	 * @throws ValidationException if the ValidatorFactory cannot be built
	 */
	public static ValidatorFactory buildDefaultValidatorFactory() {
		return byDefaultProvider().configure().buildValidatorFactory();
	}
	
	/*
	public static <T extends Configuration<T>, U extends ValidationProvider<T>>	ProviderSpecificBootstrap<T> byProvider(Class<U> providerType) {
		ProviderSpecificBootstrap<ConfigurationImpl> psBoot = new ProviderSpecificBootstrap<ConfigurationImpl>() {
			
			public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver resolver) {
				return this;
			}

			public T configure() {
				return new ConfigurationImpl();
			}
		};
		
		return psBoot;
	}
	*/

	/**
	 * Build a <code>Configuration</code>. The provider list is resolved
	 * using the strategy provided to the bootstrap state.
	 * <pre>
	 * Configuration&lt?&gt; configuration = Validation
	 *    .byDefaultProvider()
	 *    .providerResolver( new MyResolverStrategy() )
	 *    .configure();
	 * ValidatorFactory factory = configuration.buildValidatorFactory();
	 * </pre>
	 * The provider can be specified in the XML configuration. If the XML
	 * configuration does not exsist or if no provider is specified,
	 * the first available provider will be returned.
	 *
	 * @return instance building a generic <code>Configuration</code>
	 *         compliant with the bootstrap state provided.
	 */
	public static GenericBootstrap byDefaultProvider() {
		GenericBootstrap bootstrap = new GenericBootstrap() {
			
			@Override
			public GenericBootstrap providerResolver(ValidationProviderResolver resolver) {
				return this;
			}
			
			@Override
			public Configuration<?> configure() {
				return new ConfigurationImpl();
			}
			
		};		
		return bootstrap;
	}
}
