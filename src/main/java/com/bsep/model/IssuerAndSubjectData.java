package com.bsep.model;

import com.bsep.certificate.CertificateRole;
import com.bsep.certificate.CertificateStatus;
import com.bsep.certificate.TypeOfEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class IssuerAndSubjectData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String organization;

    @Column
    private String organizationUnit;

    @Column
    private String country;

    @Column
    private String city;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private TypeOfEntity typeOfEntity;

    @Enumerated(value = EnumType.STRING)
    private CertificateRole certificateRole;

    @Transient
    private String firstNameSubject;

    @Transient
    private String lastNameSubject;

    @Transient
    private String organizationSubject;

    @Transient
    private String organizationUnitSubject;

    @Transient
    private String countrySubject;

    @Transient
    private String citySubject;

    @Transient
    private String emailSubject;

    @Transient
    private String phoneSubject;

    @Column
    private boolean[] keyUsage;

    @Column
    private boolean[] extendedKeyUsage;

    @Enumerated(value = EnumType.STRING)
    private CertificateStatus certificateStatus;

    @Column
    private Long parent;

    @Column
    Date startDate;

    @Column
    Date expiringDate;

    public IssuerAndSubjectData() {
    }

    public IssuerAndSubjectData(String firstName, String lastName, String organization, String organizationUnit,
                                String country, String city, String email, String phone, String firstNameSubject,
                                String lastNameSubject, String organizationSubject, String organizationUnitSubject,
                                String countrySubject, String citySubject, String emailSubject, String phoneSubject,
                                boolean[] keyUsage,boolean[] extendedKeyUsage) {
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
        this.certificateStatus = CertificateStatus.VALID;
        this.keyUsage = keyUsage;
        this.extendedKeyUsage = extendedKeyUsage;
    }

    public IssuerAndSubjectData(String firstName, String lastName, String organization, String organizationUnit,
                                String country, String city, String email, String phone, TypeOfEntity typeOfEntity,
                                CertificateRole certificateRole,boolean[] keyUsage,boolean[] extendedKeyUsage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.typeOfEntity = typeOfEntity;
        this.certificateRole = certificateRole;
        this.certificateStatus = CertificateStatus.VALID;
        this.keyUsage = keyUsage;
        this.extendedKeyUsage = extendedKeyUsage;
    }

    public IssuerAndSubjectData(Long id, String firstName, String lastName, String organization, String organizationUnit, String country, String city, String email, String phone, TypeOfEntity typeOfEntity, CertificateRole certificateRole, String firstNameSubject, String lastNameSubject, String organizationSubject, String organizationUnitSubject, String countrySubject, String citySubject, String emailSubject, String phoneSubject, boolean[] keyUsage, boolean[] extendedKeyUsage, CertificateStatus certificateStatus, Long parent, Date startDate, Date expiringDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.typeOfEntity = typeOfEntity;
        this.certificateRole = certificateRole;
        this.firstNameSubject = firstNameSubject;
        this.lastNameSubject = lastNameSubject;
        this.organizationSubject = organizationSubject;
        this.organizationUnitSubject = organizationUnitSubject;
        this.countrySubject = countrySubject;
        this.citySubject = citySubject;
        this.emailSubject = emailSubject;
        this.phoneSubject = phoneSubject;
        this.keyUsage = keyUsage;
        this.extendedKeyUsage = extendedKeyUsage;
        this.certificateStatus = certificateStatus;
        this.parent = parent;
        this.startDate = startDate;
        this.expiringDate = expiringDate;
    }

    public boolean[] getExtendedKeyUsage() {
        return extendedKeyUsage;
    }

    public void setExtendedKeyUsage(boolean[] extendedKeyUsage) {
        this.extendedKeyUsage = extendedKeyUsage;
    }

    public boolean[] getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(boolean[] keyUsage) {
        this.keyUsage = keyUsage;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfEntity getTypeOfEntity() {
        return typeOfEntity;
    }

    public void setTypeOfEntity(TypeOfEntity typeOfEntity) {
        this.typeOfEntity = typeOfEntity;
    }

    public CertificateRole getCertificateRole() {
        return certificateRole;
    }

    public void setCertificateRole(CertificateRole certificateRole) {
        this.certificateRole = certificateRole;
    }

    public CertificateStatus getCertificateStatus() { return certificateStatus; }

    public void setCertificateStatus(CertificateStatus certificateStatus) { this.certificateStatus = certificateStatus; }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getExpiringDate() { return expiringDate; }

    public void setExpiringDate(Date expiringDate) { this.expiringDate = expiringDate; }
}
