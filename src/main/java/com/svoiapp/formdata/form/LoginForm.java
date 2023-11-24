package com.svoiapp.formdata.form;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginForm {
    private final String login;
    private final String password;

    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
