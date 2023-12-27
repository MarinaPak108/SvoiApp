package com.svoiapp.formdata;

public class CreateVisaExtendFormData {
    private String name;
    private String surname;
    private String address;
    private String visatype;

    public CreateVisaExtendFormData() {
    }

    public CreateVisaExtendFormData(String name, String surname, String address, String visatype) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.visatype = visatype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVisatype() {
        return visatype;
    }

    public void setVisatype(String visatype) {
        this.visatype = visatype;
    }
}
