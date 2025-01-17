package com.svoiapp.formdata.form;

public class UserCreationParameters {
    private final String login;
    private final String password;
    private final String email;

    public UserCreationParameters(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
