package com.bsep.dto;

public class DownloadCertificateDTO {

    private String role;
    private String keyStorePassword;
    private String alias;

    public DownloadCertificateDTO() {
    }

    public DownloadCertificateDTO(String role, String keyStorePassword, String alias) {
        this.role = role;
        this.keyStorePassword = keyStorePassword;
        this.alias = alias;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
