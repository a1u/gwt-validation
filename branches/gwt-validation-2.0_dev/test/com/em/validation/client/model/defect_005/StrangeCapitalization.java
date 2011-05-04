package com.em.validation.client.model.defect_005;

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
