package com.svoiapp.formdata;

import java.lang.reflect.Field;

public class CreateVisaExtendFormData {
    private String reason;
    private String surname;
    private String name;
    private String bdate;
    private String sex;
    private String nationality;
    private String idNumber;
    private String passno;
    private String passdate;
    private String passexp;
    private String koraddress;
    private String mobileno;
    private String homeaddress;
    private String schoolstatus;
    private String schoolname;
    private String schooltel;
    private String schooltype;
    private String position;
    private String compname;
    private String compno;
    private String comptel;
    private String compnameNew;
    private String compnoNew;
    private String comptelNew;
    private String salary;
    private String bankno;

    public CreateVisaExtendFormData() {
    }

    public CreateVisaExtendFormData(String reason,
                                    String surname,
                                    String name,
                                    String bdate,
                                    String sex,
                                    String nationality,
                                    String idNumber,
                                    String passno,
                                    String passdate,
                                    String passexp,
                                    String koraddress,
                                    String mobileno,
                                    String homeaddress,
                                    String schoolstatus,
                                    String schoolname,
                                    String schooltel,
                                    String schooltype,
                                    String position,
                                    String compname,
                                    String compno,
                                    String comptel,
                                    String compnameNew,
                                    String compnoNew,
                                    String comptelNew,
                                    String salary,
                                    String bankno) {
        this.reason = reason;
        this.surname = surname;
        this.name = name;
        this.bdate = bdate;
        this.sex = sex;
        this.nationality = nationality;
        this.idNumber = idNumber;
        this.passno = passno;
        this.passdate = passdate;
        this.passexp = passexp;
        this.koraddress = koraddress;
        this.mobileno = mobileno;
        this.homeaddress = homeaddress;
        this.schoolstatus = schoolstatus;
        this.schoolname = schoolname;
        this.schooltel = schooltel;
        this.schooltype = schooltype;
        this.position = position;
        this.compname = compname;
        this.compno = compno;
        this.comptel = comptel;
        this.compnameNew = compnameNew;
        this.compnoNew = compnoNew;
        this.comptelNew = comptelNew;
        this.salary = salary;
        this.bankno = bankno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassno() {
        return passno;
    }

    public void setPassno(String passno) {
        this.passno = passno;
    }

    public String getPassdate() {
        return passdate;
    }

    public void setPassdate(String passdate) {
        this.passdate = passdate;
    }

    public String getPassexp() {
        return passexp;
    }

    public void setPassexp(String passexp) {
        this.passexp = passexp;
    }

    public String getKoraddress() {
        return koraddress;
    }

    public void setKoraddress(String koraddress) {
        this.koraddress = koraddress;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getSchoolstatus() {
        return schoolstatus;
    }

    public void setSchoolstatus(String schoolstatus) {
        this.schoolstatus = schoolstatus;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSchooltel() {
        return schooltel;
    }

    public void setSchooltel(String schooltel) {
        this.schooltel = schooltel;
    }

    public String getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public String getCompno() {
        return compno;
    }

    public void setCompno(String compno) {
        this.compno = compno;
    }

    public String getComptel() {
        return comptel;
    }

    public void setComptel(String comptel) {
        this.comptel = comptel;
    }

    public String getCompnameNew() {
        return compnameNew;
    }

    public void setCompnameNew(String compnameNew) {
        this.compnameNew = compnameNew;
    }

    public String getCompnoNew() {
        return compnoNew;
    }

    public void setCompnoNew(String compnoNew) {
        this.compnoNew = compnoNew;
    }

    public String getComptelNew() {
        return comptelNew;
    }

    public void setComptelNew(String comptelNew) {
        this.comptelNew = comptelNew;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public CreateVisaExtendFormData convertNullFieldsToString (CreateVisaExtendFormData data){
        // Get the class of the provided entity
        Class<?> clazz = data.getClass();

        // Get all fields of the entity class
        Field[] fields = clazz.getDeclaredFields();

        // Iterate over each field
        for (Field field : fields) {
            try {
                // Get the value of the field from the entity object
                Object value = field.get(data);

                if(value == null){
                    // Ensure field is accessible (even if it's private)
                    field.setAccessible(true);
                    // Set the field to an empty string
                    field.set(data, "");
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
