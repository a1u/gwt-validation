/*
 * Copyright (c) 2009 Nordic Consulting & Development Company.
 * All rights reserved.
 * NCDC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotEmpty;
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
@GroupSequence(name = "default", sequence = { "minimal", "full", "extended" })
public class Submission implements IValidatable {
    @NotEmpty(groups = { "minimal" })
    @NotNull(groups = { "minimal" })
    @Length(groups = { "minimal" }, minimum = 3)
    private String fullName;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(final String inFullName) {
        this.fullName = inFullName;
    }
}
