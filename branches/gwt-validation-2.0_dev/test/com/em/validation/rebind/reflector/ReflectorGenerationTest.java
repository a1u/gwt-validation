package com.em.validation.rebind.reflector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.rebind.ReflectorGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.ReflectorClassDescriptions;

public class ReflectorGenerationTest {

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
		
		
	}
	
}
