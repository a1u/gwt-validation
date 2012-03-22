package com.em.validation.client.model.defects.defect_037;

import java.io.Serializable;

public class BaseAction implements Serializable {

    private static final long serialVersionUID = -6496309699600948752L;

    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

}