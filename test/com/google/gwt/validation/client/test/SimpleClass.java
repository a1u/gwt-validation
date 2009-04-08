/*
 * Copyright (c) 2009 Nordic Consulting & Development Company.
 * All rights reserved.
 * NCDC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.NotNull;

/**
 * 
 * <p>
 * Created on 2009-04-08
 *
 * @author kardigen
 * @version $Id$
 */
public class SimpleClass extends SimpleSuperClass {
    
    @NotNull
    private Integer test;

    public void setTest(final Integer test) {
        this.test = test;
    }

    public Integer getTest() {
        return test;
    }

}
