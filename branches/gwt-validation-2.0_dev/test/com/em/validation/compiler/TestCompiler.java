package com.em.validation.compiler;

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

import com.em.validation.rebind.metadata.ClassDescriptor;

public class TestCompiler {

	@SuppressWarnings("deprecation")
	public static Class<?> loadClass(ClassDescriptor descriptor) {
		
		Class<?> returnClass = null;
		
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
		
		List<File> fileList = new ArrayList<File>();
		
		UUID randomRun = UUID.randomUUID();
		
		File dir = new File("./generation_" + randomRun.toString());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//write constraint description to file
		File tempFile;
		try {
			String fileName = descriptor.getFullClassName();
			fileName = fileName.replaceAll("\\.", "/");
			tempFile = new File(dir.getAbsolutePath() + "/" + fileName + ".java");
			tempFile.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(tempFile);
			writer.write(descriptor.getClassContents());
			writer.flush();
			writer.close();
			
			//add file to list
			fileList.add(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//add task to jc
		Iterable<? extends JavaFileObject> fileObjects = sjfm.getJavaFileObjects(fileList.toArray(new File[]{}));
		
		//String[] options = new String[]{"-Xlint:unchecked"};
		//CompilationTask task = jc.getTask(null, sjfm, null, Arrays.asList(options), null, fileObjects);
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
			e.printStackTrace();
		}
		if(urls.length > 0) {
			URLClassLoader ucl = new URLClassLoader(urls);
			try {
				returnClass = ucl.loadClass(descriptor.getFullClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//delete when we close, to clean up cruft
		dir.deleteOnExit();
		
		return returnClass;
	}
	
}
