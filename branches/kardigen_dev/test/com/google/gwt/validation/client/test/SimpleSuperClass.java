/*
 * Copyright (c) 2009 Nordic Consulting & Development Company.
 * All rights reserved.
 * NCDC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

/**
 * 
 * <p>
 * Created on 2009-04-08
 *
 * @author kardigen
 * @version $Id$
 */
public class SimpleSuperClass implements IValidatable {

    @NotNull
    private String testSuper;

    public void setTestSuper(final String testSuper) {
        this.testSuper = testSuper;
    }

    public String getTestSuper() {
        return testSuper;
    }

    
}
