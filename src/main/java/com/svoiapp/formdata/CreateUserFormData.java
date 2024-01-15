package com.svoiapp.formdata;

import com.svoiapp.formdata.form.UserCreationParameters;
import com.svoiapp.validation.ValidPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserFormData {
    @NotNull
    @Size(min =3, max=255)
    private String login;

    @NotNull
    @Size(min =3, max=255)
    @ValidPassword
    private String pwd;

    @NotNull
    @Size(min =3, max=255)
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserCreationParameters toParameters(){
        return new UserCreationParameters(login, pwd, email);
    }
}
