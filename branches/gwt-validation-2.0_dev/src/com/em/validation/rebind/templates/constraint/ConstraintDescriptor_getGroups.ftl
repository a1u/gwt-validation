		Set<Class<?>> result = new HashSet<Class<?>>(Arrays.asList(this.annotation.groups()));
		if(result.size() == 0) {
			result.add(javax.validation.groups.Default.class);
		}
		return result;