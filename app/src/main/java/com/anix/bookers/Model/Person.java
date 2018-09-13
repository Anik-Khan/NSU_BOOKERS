package com.anix.bookers.Model;

/**
 * Created by Anix on 4/8/2018.
 */

public class Person {

    private String Name, Email, PhoneNo, NSUID, Gender,AuthKy;

    public Person() {
    }

    public Person(String name, String email, String phoneNo, String NSUID, String gender, String authKy) {
        Name = name;
        Email = email;
        PhoneNo = phoneNo;
        this.NSUID = NSUID;
        Gender = gender;
        AuthKy = authKy;
    }

    public String getAuthKy() {
        return AuthKy;
    }

    public void setAuthKy(String authKy) {
        AuthKy = authKy;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getNSUID() {
        return NSUID;
    }

    public void setNSUID(String NSUID) {
        this.NSUID = NSUID;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
