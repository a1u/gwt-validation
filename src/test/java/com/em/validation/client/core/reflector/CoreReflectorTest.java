package com.em.validation.client.core.reflector;

import javax.validation.Validation;
import javax.validation.Validator;

import com.em.validation.client.model.reflector.JustInterface01;
import com.em.validation.client.model.reflector.JustInterface02;
import com.em.validation.client.model.reflector.OnlyInterfaces;
import com.em.validation.client.model.reflector.ReflectiveBase;
import com.em.validation.client.model.reflector.ReflectiveExposed;
import com.em.validation.client.model.reflector.ReflectiveIntermediary;
import com.em.validation.client.model.reflector.ReflectiveMiddle;
import com.em.validation.client.model.reflector.ReflectiveSkip;
import com.em.validation.client.model.reflector.ReflectiveTop;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreReflectorTest {

	private static class ReflectiveUneeded extends ReflectiveExposed {
		
	}
	
	public static void testDeepReflectionChain(ITestCase testCase) {
		//get factory
		IReflectorFactory factory = testCase.getReflectorFactory();
		
		//get reflection chain, starting with the most exposed class
		IReflector rExposed = factory.getReflector(ReflectiveExposed.class);
		IReflector rItermediary = rExposed.getParentReflector();
		IReflector rSkip = rItermediary.getParentReflector();
		IReflector rTop = rSkip.getParentReflector();
		IReflector rMid = rTop.getParentReflector();
		IReflector rBase = rMid.getParentReflector();
		
		//make assertions
		testCase.localAssertNotNull(rExposed);
		testCase.localAssertNotNull(rItermediary);
		testCase.localAssertNotNull(rSkip);
		testCase.localAssertNotNull(rTop);
		testCase.localAssertNotNull(rMid);
		testCase.localAssertNotNull(rBase);		
		
		//nothing after rBase
		testCase.localAssertTrue(rBase.getParentReflector() == null || rBase.getParentReflector().getTargetClass() == null);
		
		//make assertions about target class for each reflector
		testCase.localAssertEquals(ReflectiveExposed.class, rExposed.getTargetClass());
		testCase.localAssertEquals(ReflectiveIntermediary.class, rItermediary.getTargetClass());
		testCase.localAssertEquals(ReflectiveSkip.class, rSkip.getTargetClass());
		testCase.localAssertEquals(ReflectiveTop.class, rTop.getTargetClass());
		testCase.localAssertEquals(ReflectiveMiddle.class, rMid.getTargetClass());
		testCase.localAssertEquals(ReflectiveBase.class, rBase.getTargetClass());
		
		//create object
		ReflectiveExposed refExp = new ReflectiveExposed();
		refExp.setReflectiveBaseString("base");
		refExp.setReflectiveIntermediaryLayerString("inter");
		refExp.setReflectiveMiddleString("mid");
		
		//invoke methods, descending down the *class only* hierarchy, interfaces later
		testCase.localAssertEquals("base", rExposed.getValue("reflectiveBaseString", refExp));
		testCase.localAssertEquals("inter", rExposed.getValue("reflectiveIntermediaryLayerString", refExp));
		testCase.localAssertEquals("mid", rExposed.getValue("reflectiveMiddleString", refExp));
		
		testCase.localAssertEquals("base", rItermediary.getValue("reflectiveBaseString", refExp));
		testCase.localAssertEquals("inter", rItermediary.getValue("reflectiveIntermediaryLayerString", refExp));
		testCase.localAssertEquals("mid", rItermediary.getValue("reflectiveMiddleString", refExp));

		testCase.localAssertEquals("base", rSkip.getValue("reflectiveBaseString", refExp));
		testCase.localAssertEquals("mid", rSkip.getValue("reflectiveMiddleString", refExp));
		
		testCase.localAssertEquals("base", rTop.getValue("reflectiveBaseString", refExp));
		testCase.localAssertEquals("mid", rTop.getValue("reflectiveMiddleString", refExp));
		
		testCase.localAssertEquals(null, rMid.getValue("reflectiveIntermediaryLayerString", refExp));
		testCase.localAssertEquals("base", rMid.getValue("reflectiveBaseString", refExp));
		testCase.localAssertEquals("mid", rMid.getValue("reflectiveMiddleString", refExp));

		testCase.localAssertEquals(null, rBase.getValue("reflectiveIntermediaryLayerString", refExp));
		testCase.localAssertEquals(null, rBase.getValue("reflectiveMiddleString", refExp));
		testCase.localAssertEquals("base", rBase.getValue("reflectiveBaseString", refExp));
		
		//now look at interfaces, from the viewpoint of the exposed class
		testCase.localAssertEquals("reflectiveSide01AtIntermediaryLayerString", rExposed.getValue("reflectiveSide01AtIntermediaryLayerString", refExp));
		testCase.localAssertEquals("reflectiveSide02AtIntermediaryLayerString", rExposed.getValue("reflectiveSide02AtIntermediaryLayerString", refExp));
		testCase.localAssertEquals("reflectiveSideAtTopTierString", rExposed.getValue("reflectiveSideAtTopTierString", refExp));
		
		//get validator
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		//validate new instances
		testCase.localAssertEquals(6, validator.validate(new ReflectiveExposed()).size());
		testCase.localAssertEquals(6, validator.validate(new ReflectiveIntermediary()).size());
		testCase.localAssertEquals(3, validator.validate(new ReflectiveSkip()).size());
		testCase.localAssertEquals(3, validator.validate(new ReflectiveTop()).size());
		testCase.localAssertEquals(2, validator.validate(new ReflectiveMiddle()).size());
		testCase.localAssertEquals(1, validator.validate(new ReflectiveBase()).size());
		
		//anonymous class test
		ReflectiveExposed rexAnon = new ReflectiveExposed(){
			
		};
		testCase.localAssertEquals(6, validator.validate(rexAnon).size());
		
		IReflector rAnon = factory.getReflector(rexAnon.getClass());
		
		rexAnon.setReflectiveBaseString("base");
		rexAnon.setReflectiveIntermediaryLayerString("inter");
		rexAnon.setReflectiveMiddleString("mid");
		
		testCase.localAssertEquals("base", rExposed.getValue("reflectiveBaseString", rexAnon));
		testCase.localAssertEquals("inter", rExposed.getValue("reflectiveIntermediaryLayerString", rexAnon));
		testCase.localAssertEquals("mid", rExposed.getValue("reflectiveMiddleString", rexAnon));
		testCase.localAssertEquals("reflectiveSide01AtIntermediaryLayerString", rAnon.getValue("reflectiveSide01AtIntermediaryLayerString", rexAnon));
		testCase.localAssertEquals("reflectiveSide02AtIntermediaryLayerString", rAnon.getValue("reflectiveSide02AtIntermediaryLayerString", rexAnon));
		testCase.localAssertEquals("reflectiveSideAtTopTierString", rAnon.getValue("reflectiveSideAtTopTierString", rexAnon));
		
		//local class test
		class ReflectiveLocal extends ReflectiveUneeded {
			
		};
		ReflectiveLocal local = new ReflectiveLocal();
		
		IReflector rLocal = factory.getReflector(local.getClass());
		
		local.setReflectiveBaseString("base");
		local.setReflectiveIntermediaryLayerString("inter");
		local.setReflectiveMiddleString("mid");
		
		testCase.localAssertEquals("base", rExposed.getValue("reflectiveBaseString", local));
		testCase.localAssertEquals("inter", rExposed.getValue("reflectiveIntermediaryLayerString", local));
		testCase.localAssertEquals("mid", rExposed.getValue("reflectiveMiddleString", local));
		testCase.localAssertEquals("reflectiveSide01AtIntermediaryLayerString", rLocal.getValue("reflectiveSide01AtIntermediaryLayerString", local));
		testCase.localAssertEquals("reflectiveSide02AtIntermediaryLayerString", rLocal.getValue("reflectiveSide02AtIntermediaryLayerString", local));
		testCase.localAssertEquals("reflectiveSideAtTopTierString", rLocal.getValue("reflectiveSideAtTopTierString", local));
		testCase.localAssertEquals(6, validator.validate(new ReflectiveLocal()).size());

	}
	
	public static void testAnonymousInterfaceImplementation(ITestCase testCase) {
		//get factory
		IReflectorFactory factory = testCase.getReflectorFactory();
		
		JustInterface01 interface01_01 = new JustInterface01() {
			@Override
			public String getInterface01String() {
				return "interface01String";
			}

			@Override
			public String getJustBaseInterface() {
				return "baseInterfaceString";
			}
		};
		
		IReflector reflector = factory.getReflector(interface01_01.getClass());
		testCase.localAssertEquals("interface01String", reflector.getValue("interface01String", interface01_01));
		
	}
	
	private static class OnlyInterfacesRedirect extends OnlyInterfaces {
		
	}
	
	public static void testClassWithNoConstraintsButConstrainedInterfaces(ITestCase testCase) {
		//get factory
		IReflectorFactory factory = testCase.getReflectorFactory();
		
		JustInterface01 iface01 = new OnlyInterfaces();
		JustInterface02 iface02 = new OnlyInterfaces();
		
		OnlyInterfaces iface03 = new OnlyInterfaces(){
			
		};
		
		OnlyInterfacesRedirect iface04 = new OnlyInterfacesRedirect();
		
		IReflector reflector01 = factory.getReflector(iface01.getClass());
		IReflector reflector02 = factory.getReflector(iface02.getClass());
		IReflector reflector03 = factory.getReflector(iface03.getClass());
		IReflector reflector04 = factory.getReflector(iface04.getClass());
		
		testCase.localAssertEquals("interface01String", reflector01.getValue("interface01String", iface01));
		testCase.localAssertEquals("interface02String", reflector01.getValue("interface02String", iface01));
				
		testCase.localAssertEquals("interface01String", reflector02.getValue("interface01String", iface02));
		testCase.localAssertEquals("interface02String", reflector02.getValue("interface02String", iface02));
		
		testCase.localAssertEquals("interface01String", reflector03.getValue("interface01String", iface03));
		testCase.localAssertEquals("interface02String", reflector03.getValue("interface02String", iface03));
		
		testCase.localAssertEquals("interface01String", reflector04.getValue("interface01String", iface04));
		testCase.localAssertEquals("interface02String", reflector04.getValue("interface02String", iface04));
	}
	
}
