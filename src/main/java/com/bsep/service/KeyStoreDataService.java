package com.bsep.service;

import com.bsep.certificate.CertificateRole;
import com.bsep.model.KeyStoreData;
import org.bouncycastle.jcajce.provider.asymmetric.X509;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface KeyStoreDataService {
    boolean doesKeyStoreExist(String certificateRole);

    X509Certificate loadCertificate(String role, String alias, String password) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
}
