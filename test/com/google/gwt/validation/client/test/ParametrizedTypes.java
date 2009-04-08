/*
 * Copyright (c) 2009 Nordic Consulting & Development Company.
 * All rights reserved.
 * NCDC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.google.gwt.validation.client.test;

import java.util.Collection;

import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

/**
 * 
 * <p>
 * Created on 2009-04-08
 *
 * @author kardigen
 * @version $Id$
 */
public class ParametrizedTypes implements IValidatable {
    
    @Valid
    private Collection<? extends SimpleSuperClass> parametrizedCollection;

    public void setParametrizedCollection(final Collection<? extends SimpleSuperClass> parametrizedCollection) {
        this.parametrizedCollection = parametrizedCollection;
    }

    public Collection<? extends SimpleSuperClass> getParametrizedCollection() {
        return parametrizedCollection;
    }
    
}
