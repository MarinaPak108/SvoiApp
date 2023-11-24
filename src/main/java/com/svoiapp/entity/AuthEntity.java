package com.svoiapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authorities")
public class AuthEntity {

    @Id
    @Column(name = "authid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Basic
    @Column(name = "name", length = 255)
    private String name;

    @OneToMany
    @JoinColumn(name = "auth")
    private List<DataEntity> dataEntities;

    public AuthEntity(Integer authId, String name, List<DataEntity> dataEntities) {
        this.authId = authId;
        this.name = name;
        this.dataEntities = dataEntities;
    }

    public AuthEntity() {

    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataEntity> getDataEntities() {
        return dataEntities;
    }

    public void setDataEntities(List<DataEntity> dataEntities) {
        this.dataEntities = dataEntities;
    }

    @Override
    public String toString() {
        return "AuthEntity{" +
                "authId=" + authId +
                ", name='" + name + '\'' +
                ", dataEntities=" + getMemebersAsString() +
                '}';
    }

    public List<DataEntity> getMemebers(){
        List<DataEntity> entities = dataEntities.stream().toList();
        return entities;
    }

    public List<String> getMemebersByLogin(){
        List <String> logins = new ArrayList<>();
        dataEntities.stream().
                map(c-> c.getLogin()).
                forEach(logins:: add);
        return logins;
    }

    public String getMemebersAsString(){
        String logins = dataEntities.stream().
                map(d->d.getLogin()).
                collect(Collectors.joining(" , "));
        return logins;
    }
}
