/*
 * Copyright (c) 2009 Nordic Consulting & Development Company.
 * All rights reserved.
 * NCDC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.google.gwt.validation.client.test;

import java.util.Date;

import com.google.gwt.validation.client.AssertFalse;
import com.google.gwt.validation.client.AssertTrue;
import com.google.gwt.validation.client.Email;
import com.google.gwt.validation.client.Future;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Past;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.Range;
import com.google.gwt.validation.client.Size;
import com.google.gwt.validation.client.interfaces.IValidatable;

/**
 * 
 * <p>
 * Created on 2009-04-16
 *
 * @author kardigen
 * @version $Id$
 */
public class Validators implements IValidatable {
    
    
    

    @NotNull
    String notNullTrue;

    @NotNull
    String notNullFalse;
    
    @NotEmpty
    String notEmptyTrue;
    
    @NotEmpty
    String notEmptyFalse;
    
    @Max(maximum=2)
    int minimumTwoTrue;
    
    @Max(maximum=2)
    int minimumTwoFalse;
    
    @Min(minimum=2)
    int maximumTwoTrue;
    
    @Min(minimum=2)
    int maximumTwoFalse;
    
    @Range(minimum=2,maximum=10)
    float valueInRange2to10;
    
    @Range(minimum=2,maximum=10)
    float valueInRange2to10Over;
    
    @Range(minimum=2,maximum=10)
    float valueInRange2to10Under;
    
    @AssertTrue
    boolean shouldBeTrue;
    
    @AssertTrue
    boolean shouldNotBeTrue;
    
    @AssertFalse
    boolean shouldBeFalse;
    
    @AssertFalse
    boolean shouldNotBeFalse;
    
    @Email
    String emailTrue;
    
    @Email
    String emailFalse;
    
    @Future
    Date futureTrue;
    
    @Future
    Date futureFalse;
    
    @Past
    Date pastTrue;
    
    @Past
    Date pastFalse;
    
    @Length(minimum=2,maximum=10)
    String stringRange2to10;
    
    @Length(minimum=2,maximum=10)
    String stringRange2to10Over;
    
    @Length(minimum=2,maximum=10)
    String stringRange2to10Under;
    
    @Pattern(pattern="[ABC]+")
    String patternABCTrue;
    
    @Pattern(pattern="[ABC]+")
    String patternABCFalse;
    
    @Pattern(pattern="[a-zA-Z0-9\\._-]")
    String patternAZTrue;
    
    @Size(minimum=1,maximum=1)
    String[] arrayOf2;
    
    @Size(minimum=1,maximum=1)
    String[] arrayOf2Over;

    @Size(minimum=1,maximum=1)
    String[] arrayOf2Under;
    
    public Validators() {
        
        this.notNullTrue = "";
        this.notNullFalse = null;
        this.notEmptyTrue = "?";
        this.notEmptyFalse = "";
        this.minimumTwoTrue = 2;
        this.minimumTwoFalse = 3;
        this.maximumTwoTrue = 2;
        this.maximumTwoFalse = 1;
        this.valueInRange2to10 = 5;
        this.valueInRange2to10Over = 11;
        this.valueInRange2to10Under = 1;
        this.shouldBeTrue = true;
        this.shouldNotBeTrue = false;
        this.shouldBeFalse = false;
        this.shouldNotBeFalse = true;
        this.emailTrue = "a@aa.com";
        this.emailFalse = "a.2.";
        this.futureTrue = new Date(new Date().getTime() + 10000); //I hope that is enough 
        this.futureFalse = new Date();
        this.pastTrue = new Date(new Date().getTime() - 10);
        this.pastFalse = new Date(new Date().getTime() + 10000);
        this.stringRange2to10 = "asdf";
        this.stringRange2to10Over = "stringRange2to10Over";
        this.stringRange2to10Under = "";
        this.patternABCTrue = "ABC";
        this.patternABCFalse = "FFF";
        this.patternAZTrue = ".";
        this.arrayOf2 = new String[]{"a","b"};
        this.arrayOf2Over = new String[]{"a","b","c"};
        this.arrayOf2Under = new String[]{"a"};
    }
    
    public String[] getArrayOf2() {
        return arrayOf2;
    }

    public String[] getArrayOf2Over() {
        return arrayOf2Over;
    }

    public String[] getArrayOf2Under() {
        return arrayOf2Under;
    }

    public String getEmailFalse() {
        return emailFalse;
    }

    public String getEmailTrue() {
        return emailTrue;
    }

    public Date getFutureFalse() {
        return futureFalse;
    }

    public Date getFutureTrue() {
        return futureTrue;
    }

    public int getMaximumTwoFalse() {
        return maximumTwoFalse;
    }

    public int getMaximumTwoTrue() {
        return maximumTwoTrue;
    }

    public int getMinimumTwoFalse() {
        return minimumTwoFalse;
    }

    public int getMinimumTwoTrue() {
        return minimumTwoTrue;
    }

    public String getNotEmptyFalse() {
        return notEmptyFalse;
    }

    public String getNotEmptyTrue() {
        return notEmptyTrue;
    }

    public String getNotNullFalse() {
        return notNullFalse;
    }

    public String getNotNullTrue() {
        return notNullTrue;
    }

    public Date getPastFalse() {
        return pastFalse;
    }

    public Date getPastTrue() {
        return pastTrue;
    }

    public String getPatternABCFalse() {
        return patternABCFalse;
    }

    public String getPatternABCTrue() {
        return patternABCTrue;
    }

    public String getPatternAZTrue() {
        return patternAZTrue;
    }

    public String getStringRange2to10() {
        return stringRange2to10;
    }

    public String getStringRange2to10Over() {
        return stringRange2to10Over;
    }

    public String getStringRange2to10Under() {
        return stringRange2to10Under;
    }

    public float getValueInRange2to10() {
        return valueInRange2to10;
    }

    public float getValueInRange2to10Over() {
        return valueInRange2to10Over;
    }

    public float getValueInRange2to10Under() {
        return valueInRange2to10Under;
    }

    public boolean isShouldBeFalse() {
        return shouldBeFalse;
    }

    public boolean isShouldBeTrue() {
        return shouldBeTrue;
    }

    public boolean isShouldNotBeFalse() {
        return shouldNotBeFalse;
    }

    public boolean isShouldNotBeTrue() {
        return shouldNotBeTrue;
    }
    
}
