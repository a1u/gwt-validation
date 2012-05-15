package com.em.validation.client.model.defects.defect_037;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Taken from model provided in issue #037 (http://code.google.com/p/gwt-validation/issues/detail?id=37)
 * 
 * @author chris
 *
 */
public class Login extends BaseAction implements Action<LoginResult> {

    private static final long serialVersionUID = 2642114073817999750L;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[-\\w\\s]+")
    private String loginName;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;
	
	public Login() {
		
	}	
    public Login(final String loginName, final String password) {
        super();
        this.loginName = loginName;
        this.password = password;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
	
}
