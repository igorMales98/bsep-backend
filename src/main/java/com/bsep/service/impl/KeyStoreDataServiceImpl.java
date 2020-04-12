package com.bsep.service.impl;

import com.bsep.service.KeyStoreDataService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class KeyStoreDataServiceImpl implements KeyStoreDataService {


    @Override
    public boolean doesKeyStoreExist(String certificateRole) {
        String name = certificateRole.toLowerCase();
        File file = new File("keystores/" + name + ".jks");
        return file.exists();
    }

    @Override
    public X509Certificate loadCertificate(String role, String alias, String password) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        //kreiramo instancu KeyStore
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        //ucitavamo podatke
        String keyStoreFile = "keystores/" + role.toLowerCase() + ".jks";
        ks.load(new FileInputStream(keyStoreFile), password.toCharArray());

        System.out.println("velicina key stora" + ks.size());
        System.out.println("Ovde je dosao znaci ima fajla. i ovo je alias " + alias);
        if (ks.containsAlias(alias)) {
            System.out.println("Stampa ako postoji sa alijasom");
            return (X509Certificate) ks.getCertificate(alias);
        }
        return null;
    }
}
