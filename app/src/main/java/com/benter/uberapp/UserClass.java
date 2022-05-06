package com.benter.uberapp;

public class UserClass {

    private String nFirstName, nSecondName, nEmailAddress,nUserName, nPhoneNumber;



    public UserClass() {
    }

    public UserClass(String nFirstname, String nSecondName, String nEmailAddress, String nUserName, String nPhoneNumber) {
        this.nFirstName = nFirstname;
        this.nSecondName = nSecondName;
        this.nEmailAddress = nEmailAddress;
        this.nUserName = nUserName;
        this.nPhoneNumber = nPhoneNumber;
    }

    public String getnFirstname() {
        return nFirstName;
    }

    public void setnFirstname(String nFirstname) {
        this.nFirstName = nFirstname;
    }

    public String getnSecondName() {
        return nSecondName;
    }

    public void setnSecondName(String nSecondName) {
        this.nSecondName = nSecondName;
    }

    public String getnEmailAddress() {
        return nEmailAddress;
    }

    public void setnEmailAddress(String nEmailAddress) {
        this.nEmailAddress = nEmailAddress;
    }

    public String getnUserName() {
        return nUserName;
    }

    public void setnUserName(String nUserName) {
        this.nUserName = nUserName;
    }

    public String getnPhoneNumber() {
        return nPhoneNumber;
    }

    public void setnPhoneNumber(String nPhoneNumber) {
        this.nPhoneNumber = nPhoneNumber;
    }
}
