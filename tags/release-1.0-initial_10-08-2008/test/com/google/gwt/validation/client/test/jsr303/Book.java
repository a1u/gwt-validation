package com.google.gwt.validation.client.test.jsr303;

import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

@GroupSequence(name="default", sequence={"first", "second", "last"})
public class Book implements IValidatable {
	@NotEmpty(groups="first")
	private String title;
	
	@Length(maximum=30, groups="second")
	private String subtitle;
	
	@Valid
	@NotNull(groups="first")
	private Author author;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
}
