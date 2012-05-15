package com.em.validation.client.model.validation.notempty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.em.validation.client.constraints.NotEmpty;

public class ShouldNotBeEmptyButIs {

	@NotEmpty
	private String string = "";

	@NotEmpty
	private List<Object> list = new ArrayList<Object>();
	
	@NotEmpty
	private Map<Object, Object> map = new HashMap<Object, Object>();

	@NotEmpty
	private Object[] array = new Object[]{};

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public Object[] getArray() {
		return array;
	}

	public void setArray(Object[] array) {
		this.array = array;
	}
	
}
