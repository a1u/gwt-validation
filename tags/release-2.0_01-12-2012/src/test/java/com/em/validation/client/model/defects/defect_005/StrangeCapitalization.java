package com.em.validation.client.model.defects.defect_005;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
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
