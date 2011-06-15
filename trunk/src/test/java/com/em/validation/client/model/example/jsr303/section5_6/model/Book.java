package com.em.validation.client.model.example.jsr303.section5_6.model;

/*
Based on work in the JSR-303 for provable conformity to the standard 
 
GWT Validation Framework - A JSR-303 validation framework for GWT

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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.em.validation.client.model.example.jsr303.section5_6.constraint.NotEmpty;

public class Book {
	private String title;
	private String description;
	
	@Valid
	@NotNull
	private Author author;
	
	@NotEmpty(groups={FirstLevelCheck.class, Default.class})
	@Size(max=30)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
	this.title = title;
	}
	public Author getAuthor() {
	return author;
	}
	public void setAuthor(Author author) {
	this.author = author;
	}
	public String getDescription() {
	return description;
	}
	public void setAuthor(String description) {
	this.description = description;
	}

}
