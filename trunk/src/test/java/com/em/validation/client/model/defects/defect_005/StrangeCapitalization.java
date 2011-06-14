package com.em.validation.client.model.defects.defect_005;

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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StrangeCapitalization {

	@NotNull
	private String URIs = "uri";
	
	@NotNull
	@Size(min=1)
	private String nOnStAnDarD = "nonstandard";
	
	@NotNull
	private String alllowercase = "alllowercase";
	
	@NotNull
	private String ALLUPPERCASE = "alluppercase";
	
	public String getURIs() {
		return URIs;
	}

	public void setURIs(String uRIs) {
		URIs = uRIs;
	}

	public String getnOnStAnDarD() {
		return nOnStAnDarD;
	}

	public void setnOnStAnDarD(String nOnStAnDarD) {
		this.nOnStAnDarD = nOnStAnDarD;
	}

	public String getAlllowercase() {
		return alllowercase;
	}

	public void setAlllowercase(String alllowercase) {
		this.alllowercase = alllowercase;
	}

	public String getALLUPPERCASE() {
		return ALLUPPERCASE;
	}

	public void setALLUPPERCASE(String aLLUPPERCASE) {
		ALLUPPERCASE = aLLUPPERCASE;
	}
	
}
