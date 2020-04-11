package com.bsep.service;

import com.bsep.certificate.CertificateRole;
import com.bsep.certificate.TypeOfEntity;
import com.bsep.model.IssuerAndSubjectData;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public interface CertificateService {
    void issueCertificate(IssuerAndSubjectData issuerAndSubjectData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException;
    void createKeyStore(String type, String keyStorePassword) throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException;
    void saveCertificate(CertificateRole type, String keyPassword, String alias, String keyStorePassword, PrivateKey privateKey, Certificate certificate) throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException;
}
