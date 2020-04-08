package com.bsep.service.impl;

import com.bsep.certificate.CertificateGenerator;
import com.bsep.certificate.CertificateRole;
import com.bsep.certificate.Generators;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.model.IssuerData;
import com.bsep.model.SubjectData;
import com.bsep.repository.IssuerAndSubjectDataRepository;
import com.bsep.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class CertificateServiceImpl implements CertificateService {

    private Generators generators = new Generators();

    private CertificateGenerator certificateGenerator = new CertificateGenerator();

    @Autowired
    private IssuerAndSubjectDataRepository issuerAndSubjectDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void issueCertificate(IssuerAndSubjectData issuerAndSubjectData) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {

        IssuerAndSubjectData issuerDataToDB = new IssuerAndSubjectData(issuerAndSubjectData.getFirstName(), issuerAndSubjectData.getLastName(),
                issuerAndSubjectData.getOrganization(), issuerAndSubjectData.getOrganizationUnit(), issuerAndSubjectData.getCountry(),
                issuerAndSubjectData.getCity(), issuerAndSubjectData.getEmail(), issuerAndSubjectData.getPhone(), issuerAndSubjectData.getTypeOfEntity());
        issuerAndSubjectDataRepository.save(issuerDataToDB);

        if (!issuerAndSubjectData.getCertificateRole().equals(CertificateRole.SELF_SIGNED)) {
            IssuerAndSubjectData subjectDataToDB = new IssuerAndSubjectData(issuerAndSubjectData.getFirstNameSubject(), issuerAndSubjectData.getLastNameSubject(),
                    issuerAndSubjectData.getOrganizationSubject(), issuerAndSubjectData.getOrganizationUnitSubject(), issuerAndSubjectData.getCountrySubject(),
                    issuerAndSubjectData.getCitySubject(), issuerAndSubjectData.getEmailSubject(), issuerAndSubjectData.getPhoneSubject(), issuerAndSubjectData.getTypeOfEntity());
            issuerAndSubjectDataRepository.save(subjectDataToDB);
        }

        KeyPair keyPairIssuer = generators.generateKeyPair();

        SubjectData subjectData = generators.generateSubjectData(issuerAndSubjectData.getFirstNameSubject(), issuerAndSubjectData.getLastNameSubject(),
                issuerAndSubjectData.getOrganizationSubject(), issuerAndSubjectData.getOrganizationUnitSubject(), issuerAndSubjectData.getCountrySubject(),
                issuerAndSubjectData.getCitySubject(), issuerAndSubjectData.getEmailSubject(), issuerAndSubjectData.getPhoneSubject());


        IssuerData issuerData = generators.generateIssuerData(keyPairIssuer.getPrivate(), issuerAndSubjectData.getFirstName(), issuerAndSubjectData.getLastName(),
                issuerAndSubjectData.getOrganization(), issuerAndSubjectData.getOrganizationUnit(), issuerAndSubjectData.getCountry(),
                issuerAndSubjectData.getCity(), issuerAndSubjectData.getEmail(), issuerAndSubjectData.getPhone());


        X509Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData);

        saveCertificate(issuerAndSubjectData.getCertificateRole(),"sifra",certificate.getSerialNumber().toString(),"sifrakeystore",keyPairIssuer.getPrivate(),certificate);

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");

    }

    public void createKeyStore(String type, String keyStorePassword) {
        String file = ("keystores/" + type + ".jks");
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            keyStore.load(null, keyStorePassword.toCharArray());
            keyStore.store(new FileOutputStream(file), keyStorePassword.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCertificate(CertificateRole role, String keyPassword, String alias, String keyStorePassword, PrivateKey privateKey, Certificate certificate) throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        String type = role.toString().toLowerCase();
        String file = ("keystores/" + type + ".jks");
        KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
        Certificate[] certificates = new Certificate[10000];
        certificates[0] = certificate;
        try {
            keyStore.load(new FileInputStream(file), keyStorePassword.toCharArray());
            keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), certificates); //save cert
        } catch (FileNotFoundException e) {
            createKeyStore(type, keyStorePassword);
            saveCertificate(role, keyPassword, alias, keyStorePassword, privateKey, certificate);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }

    }

}
