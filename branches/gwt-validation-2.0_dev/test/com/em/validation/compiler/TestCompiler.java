package com.em.validation.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.em.validation.rebind.metadata.ClassDescriptor;

public enum TestCompiler {
	
	INSTANCE;
	
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	
	private TestCompiler() {
		
	}

	private void writeClassFiles(ClassDescriptor descriptor, File dir, Set<File> fileList) {
		//recursively write files
		for(ClassDescriptor dep : descriptor.getDependencies()) {
			this.writeClassFiles(dep, dir, fileList);
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
	}
	
	@SuppressWarnings("deprecation")
	public Class<?> loadClass(ClassDescriptor descriptor) {
		try {
			if(Class.forName(descriptor.getFullClassName()) != null) return Class.forName(descriptor.getFullClassName());
		} catch (ClassNotFoundException e1) {
			//class can't be found, carry on.
		}
		
		Class<?> returnClass = null;
		
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
		
		Set<File> fileList = new LinkedHashSet<File>();
		
		UUID randomRun = UUID.randomUUID();
		
		File dir = new File("./gen/id_" + randomRun.toString());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//write files to "dir" and save in the file list for the compiler iterator
		this.writeClassFiles(descriptor, dir, fileList);
		
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
			URLClassLoader ucl = new URLClassLoader(urls, this.classLoader);
			try {
				returnClass = ucl.loadClass(descriptor.getFullClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//delete when we close, to clean up generated files
		//dir.deleteOnExit();
		
		return returnClass;
	}	
}
