package com.invento.ecom.viewModel;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -1929295602672590083L;

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
