package com.em.validation.rebind;

import java.util.HashMap;
import java.util.Map;

import com.em.validation.rebind.metadata.ConstraintMetadata;

public abstract class AbstractConstraintDescriptorGenerator<T> {

	private Map<String,T> cache = new HashMap<String, T>();
	
	
	public T getConstraintDescriptor(ConstraintMetadata metadata){ 
		String key = metadata.toString();
		
		T descriptor = this.get(key);

		boolean noSpecificTypeFound = false;
		
		if(descriptor == null) {
			noSpecificTypeFound = true;
		}
		
		if(descriptor == null) {
		
			descriptor = this.create(metadata);
			
			
			if(noSpecificTypeFound && !this.contains(metadata.getInstance().toString())) {
				this.put(metadata.getInstance().toString(), descriptor);
			} 
			
			this.put(key, descriptor);
			
			for(ConstraintMetadata sub : metadata.getComposedOf()) {
				this.recurse(descriptor,sub);
			}
			
		}	
		
		descriptor = this.finish(descriptor,metadata);
			
		return descriptor;
	}
	
	protected abstract T create(ConstraintMetadata metadata);
	
	protected abstract void recurse(T withDescriptor, ConstraintMetadata metadata);
	
	protected abstract T finish(T withDescriptor, ConstraintMetadata metadata);
	
	protected void put(String key, T value) {
		this.cache.put(key, value);
	}
	
	protected T get(String key) {
		return this.cache.get(key);
	}

	protected boolean contains(String key) {
		return this.cache.containsKey(key);
	}
}

