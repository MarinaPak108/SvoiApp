package com.svoiapp.formdata;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {

    @NotNull(message = "Login cannot be null")
    @Size(max = 255)
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(max = 255)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
