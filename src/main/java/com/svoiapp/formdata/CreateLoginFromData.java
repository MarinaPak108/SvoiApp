package com.svoiapp.formdata;

import com.svoiapp.formdata.form.UserCreationParameters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NotFound;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CreateLoginFromData {
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, max = 255)
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 3, max = 255)
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
