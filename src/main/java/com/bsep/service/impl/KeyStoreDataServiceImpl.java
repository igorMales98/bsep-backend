package com.bsep.service.impl;

import com.bsep.dto.DownloadCertificateDTO;
import com.bsep.service.KeyStoreDataService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @Override
    public void download(DownloadCertificateDTO downloadCertificateDTO) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
        X509Certificate certificate = this.loadCertificate(downloadCertificateDTO.getRole(), downloadCertificateDTO.getAlias(),
                downloadCertificateDTO.getKeyStorePassword());
        System.out.println("nabavio je cert");

        FileOutputStream os = new FileOutputStream("src/main/resources/data/" + downloadCertificateDTO.getRole().toLowerCase() + "_" + downloadCertificateDTO.getAlias() + ".crt");

        os.write("\n===== Podaci o izdavacu sertifikata =====\n".getBytes("US-ASCII"));
        os.write(certificate.getIssuerX500Principal().getName().getBytes("US-ASCII"));
        os.write("\n===== Podaci o vlasniku sertifikata =====\n".getBytes("US-ASCII"));
        os.write(certificate.getSubjectX500Principal().getName().getBytes("US-ASCII"));
        os.write("\n===== Sertifikat =====\n".getBytes("US-ASCII"));
        os.write(Base64.encodeBase64(certificate.getEncoded(), true));

        os.close();
    }
}
