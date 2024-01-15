package com.svoiapp.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "data")
public class DataEntity {

    @Id
    @Column(name = "dataid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @Basic
    @Column(name = "registered", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registered;

    @ManyToOne
    @JoinColumn(name = "auth")
    private AuthEntity auth;

    @Basic
    @Column(name = "login", length = 255, unique = true)
    private String login;

    @Basic
    @Column(name = "pwd", length = 255)
    private String pwd;

    @Basic
    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Basic
    @Column(name = "pin")
    private String pin;

    @Basic
    @Column(name = "confirmed")
    private Boolean confirmed;

    public DataEntity(Long dataId, Date registered, AuthEntity auth, String login, String pwd, String email, String pin, Boolean confirmed) {
        this.dataId = dataId;
        this.registered = registered;
        this.auth = auth;
        this.login = login;
        this.pwd = pwd;
        this.email = email;
        this.pin = pin;
        this.confirmed = confirmed;
    }


    public DataEntity() {

    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public AuthEntity getAuth() {
        return auth;
    }

    public void setAuth(AuthEntity auth) {
        this.auth = auth;
    }

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "dataId=" + dataId +
                ", registered=" + registered +
                ", auth=" + auth.getName() +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", pin=" + pin +
                ", confirmed=" + confirmed +
                '}';
    }
}
