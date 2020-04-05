package com.bsep.model;

public class IssuerAndSubjectData {

    private String firstName;
    private String lastName;
    private String firstNameSubject;
    private String lastNameSubject;

    public IssuerAndSubjectData() {
    }

    public IssuerAndSubjectData(String firstName, String lastName, String firstNameSubject, String lastNameSubject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameSubject = firstNameSubject;
        this.lastNameSubject = lastNameSubject;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameSubject() {
        return firstNameSubject;
    }

    public void setFirstNameSubject(String firstNameSubject) {
        this.firstNameSubject = firstNameSubject;
    }

    public String getLastNameSubject() {
        return lastNameSubject;
    }

    public void setLastNameSubject(String lastNameSubject) {
        this.lastNameSubject = lastNameSubject;
    }
}
