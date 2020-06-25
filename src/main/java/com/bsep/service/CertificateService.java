package com.bsep.service;

import com.bsep.certificate.CertificateStatus;
import com.bsep.model.IssuerAndSubjectData;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public interface CertificateService {
    void issueCertificate(IssuerAndSubjectData issuerAndSubjectData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException;
    CertificateStatus getCertificateStatus(String email);
    void withdrawCertificate(String email);

}
