package com.bsep.model;

public class IssuerAndSubjectData {

    private String firstName;
    private String lastName;
    private String organization;
    private String organizationUnit;
    private String country;
    private String city;
    private String email;
    private String phone;

    private String firstNameSubject;
    private String lastNameSubject;
    private String organizationSubject;
    private String organizationUnitSubject;
    private String countrySubject;
    private String citySubject;
    private String emailSubject;
    private String phoneSubject;

    public IssuerAndSubjectData() {
    }

    public IssuerAndSubjectData(String firstName, String lastName, String organization, String organizationUnit,
                                String country, String city, String email, String phone, String firstNameSubject,
                                String lastNameSubject, String organizationSubject, String organizationUnitSubject,
                                String countrySubject, String citySubject, String emailSubject, String phoneSubject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.firstNameSubject = firstNameSubject;
        this.lastNameSubject = lastNameSubject;
        this.organizationSubject = organizationSubject;
        this.organizationUnitSubject = organizationUnitSubject;
        this.countrySubject = countrySubject;
        this.citySubject = citySubject;
        this.emailSubject = emailSubject;
        this.phoneSubject = phoneSubject;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganizationSubject() {
        return organizationSubject;
    }

    public void setOrganizationSubject(String organizationSubject) {
        this.organizationSubject = organizationSubject;
    }

    public String getOrganizationUnitSubject() {
        return organizationUnitSubject;
    }

    public void setOrganizationUnitSubject(String organizationUnitSubject) {
        this.organizationUnitSubject = organizationUnitSubject;
    }

    public String getCountrySubject() {
        return countrySubject;
    }

    public void setCountrySubject(String countrySubject) {
        this.countrySubject = countrySubject;
    }

    public String getCitySubject() {
        return citySubject;
    }

    public void setCitySubject(String citySubject) {
        this.citySubject = citySubject;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getPhoneSubject() {
        return phoneSubject;
    }

    public void setPhoneSubject(String phoneSubject) {
        this.phoneSubject = phoneSubject;
    }
}
