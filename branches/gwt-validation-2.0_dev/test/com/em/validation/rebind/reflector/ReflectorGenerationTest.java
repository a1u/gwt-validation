package com.em.validation.rebind.reflector;

import static junit.framework.Assert.assertEquals;

import java.lang.annotation.Annotation;

import javax.validation.Payload;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import org.junit.Test;

import com.em.validation.client.model.generic.TestClass;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.ConstraintDescriptionGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;

public class ReflectorGenerationTest {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		
		Class<?> targetClass = TestClass.class;
		String propertyName = "testString";
		Size annotation = new Size(){

			@Override
			public Class<? extends Annotation> annotationType() {
				return Size.class;
			}

			@Override
			public Class<?>[] groups() {
				return new Class<?>[]{Default.class};
			}

			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}

			@Override
			public int max() {
				return 412;
			}

			@Override
			public int min() {
				return -12;
			}
			
		};
		
		ClassDescriptor descriptor = ConstraintDescriptionGenerator.INSTANCE.generateConstraintClassDescriptor(targetClass, propertyName, annotation);
		Class<?> descriptorClass = TestCompiler.loadClass(descriptor);
		
		@SuppressWarnings("unchecked")
		ConstraintDescriptor<Size> constraintDescriptor = (ConstraintDescriptor<Size>)descriptorClass.newInstance();
		
		//assertions
		assertEquals(Size.class, constraintDescriptor.getAnnotation().annotationType());
		assertEquals(annotation.max(), constraintDescriptor.getAnnotation().max());
		assertEquals(annotation.min(), constraintDescriptor.getAnnotation().min());
		assertEquals(annotation.groups()[0], constraintDescriptor.getGroups().toArray(new Class<?>[]{})[0]);
	}

	/*
	public static void main(String[] args) {
		ReflectorClassDescriptions descriptors = ReflectorGenerator.INSTANCE.getReflectorDescirptions(TestClass.class);
		
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
		
		List<File> fileList = new ArrayList<File>();
		
		UUID randomRun = UUID.randomUUID();
		
		File dir = new File("./generation_" + randomRun.toString());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		System.out.println("Random Run: " + randomRun.toString());
		
		//create files
		for(ClassDescriptor constraintDescription : descriptors.getConstraintDescriptors()) {
			//write constraint description to file
			File tempFile;
			try {
				String fileName = constraintDescription.getFullClassName();
				fileName = fileName.replaceAll("\\.", "/");
				tempFile = new File(dir.getAbsolutePath() + "/" + fileName + ".java");
				tempFile.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(tempFile);
				writer.write(constraintDescription.getClassContents());
				writer.flush();
				writer.close();
				
				//add file to list
				fileList.add(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//write constraint description to file
		File tempFile;
		try {
			String fileName = descriptors.getClassDescriptor().getFullClassName();
			fileName = fileName.replaceAll("\\.", "/");
			tempFile = new File(dir.getAbsolutePath() + "/" + fileName + ".java");
			tempFile.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(tempFile);
			writer.write(descriptors.getClassDescriptor().getClassContents());
			writer.flush();
			writer.close();
			
			//add file to list
			fileList.add(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//add task to jc
		Iterable<? extends JavaFileObject> fileObjects = sjfm.getJavaFileObjects(fileList.toArray(new File[]{}));
		
		//String[] options = new String[]{"-d", "/home/chris/test/"};
		//jc.getTask(null, sjfm, null, Arrays.asList(options), null, fileObjects).call();
		CompilationTask task = jc.getTask(null, sjfm, null, null, null, fileObjects);
		task.call();

		//close or at least try to close
		try {
			sjfm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//load files from url
		URL[] urls = new URL[]{};
		try {
			urls = new URL[]{dir.toURL()};
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(urls.length > 0) {
			URLClassLoader ucl = new URLClassLoader(urls);
			try {
				Class<?> clazz = ucl.loadClass("com.em.validation.client.reflector.generated.reflector.TestClassReflector");
				
				@SuppressWarnings("unchecked")
				IReflector<TestClass> reflector = (IReflector<TestClass>)clazz.newInstance();
				
				TestClass tester = new TestClass();
				
				System.out.println(reflector.getValue("testString", tester));
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}*/
	
}
