package com.em.validation.compiler;

/*
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
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
	
	private File dir = new File("./gen/id_" + UUID.randomUUID().toString());
	
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	
	private Set<String> fileNameLock = new HashSet<String>();
	
	private TestCompiler() {
		
	}

	private void writeClassFiles(ClassDescriptor descriptor, File dir, Set<File> fileList) {
		String fileName = descriptor.getFullClassName();
		fileName = fileName.replaceAll("\\.", "/");
		
		if(this.fileNameLock.contains(fileName)) {
			return;
		}
		
		//write constraint description to file
		File tempFile = null;
		try {
			tempFile = new File(dir.getAbsolutePath() + "/" + fileName + ".java");
			if(!tempFile.exists()) {
				tempFile.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(tempFile);
				writer.write(descriptor.getClassContents());
				writer.flush();
				writer.close();
			}
			fileList.add(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.fileNameLock.add(fileName);
		
		//recursively write files
		for(ClassDescriptor dep : descriptor.getDependencies()) {
			this.writeClassFiles(dep, dir, fileList);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Class<?> loadClass(ClassDescriptor descriptor) {
		try {
			if(Class.forName(descriptor.getFullClassName()) != null) return Class.forName(descriptor.getFullClassName());
		} catch (ClassNotFoundException e1) {
			//class can't be found, carry on.
		}
		
		//clear
		this.fileNameLock.clear();
		
		Class<?> returnClass = null;
		
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
		
		Set<File> fileList = new LinkedHashSet<File>();
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//write files to "dir" and save in the file list for the compiler iterator
		this.writeClassFiles(descriptor, dir, fileList);
		
		if(fileList.size() > 0) {
			//add task to jc
			Iterable<? extends JavaFileObject> fileObjects = sjfm.getJavaFileObjects(fileList.toArray(new File[]{}));
			
			//String[] options = new String[]{"-Xlint:unchecked"};
			//CompilationTask task = jc.getTask(null, sjfm, null, Arrays.asList(options), null, fileObjects);
			CompilationTask task = jc.getTask(null, sjfm, null, null, null, fileObjects);
			task.call();
		}

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
