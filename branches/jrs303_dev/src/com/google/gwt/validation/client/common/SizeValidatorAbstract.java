package com.google.gwt.validation.client.common;

import java.util.Collection;
import java.util.Map;


public abstract class SizeValidatorAbstract {

	protected int minimum;
	protected int maximum;

	public void initialize(Map<String, String> propertyMap) {

		/*
		 * !!!! Notice that these keys are exactly the same as the method names
		 * on the annotation !!!!
		 */

		this.minimum = Integer.parseInt(getMin(propertyMap));
		this.maximum = Integer.parseInt(getMax(propertyMap));

	}

	private String getMin(Map<String, String> propertyMap) {
		String m = propertyMap.get("minimum");
		if (m == null) {
			m = propertyMap.get("min");
		}
		return m;
	}

	private String getMax(Map<String, String> propertyMap) {
		String m = propertyMap.get("maximum");
		if (m == null) {
			m = propertyMap.get("max");
		}
		return m;
	}

	public boolean isValid(Object value) {
		if (value == null)
			return true;

		boolean valid = false;

		int size = -1;

		if (size < 0) {

			try {
				size = ((Object[]) value).length;
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

		}

		if (size < 0) {

			try {
				size = ((int[]) value).length;
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

		}

		if (size < 0) {

			try {
				size = ((float[]) value).length;
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

		}

		if (size < 0) {

			try {
				size = ((double[]) value).length;
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

		}

		if (size < 0) {

			try {
				size = ((long[]) value).length;
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

		}

		if (size < 0) {

			try {
				size = ((Collection<?>) value).size();
			} catch (Exception ex) {

			}

		}

		if (size < 0) {

			try {
				size = ((Map<?, ?>) value).size();
			} catch (Exception ex) {

			}

		}

		if (size < 0) {

			try {
				size = ((String) value).trim().length();
			} catch (Exception ex) {

			}

			// if(size >= 0 && this.maximum >= size && this.minimum <= size)
			// valid = true;

		}

		// no size found
		if (size == -1)
			return true;

		// else compute value
		if (size >= 0 && this.maximum >= size && this.minimum <= size)
			valid = true;

		return valid;
	}

}