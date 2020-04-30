package com.bsep.service.impl;

import com.bsep.certificate.CertificateGenerator;
import com.bsep.certificate.CertificateRole;
import com.bsep.certificate.Generators;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.model.IssuerData;
import com.bsep.model.SubjectData;
import com.bsep.repository.IssuerAndSubjectDataRepository;
import com.bsep.service.CertificateService;
import com.bsep.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

@Service
public class CertificateServiceImpl implements CertificateService {

    private Generators generators = new Generators();

    private CertificateGenerator certificateGenerator = new CertificateGenerator();

    @Autowired
    private IssuerAndSubjectDataRepository issuerAndSubjectDataRepository;

    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @Override
    public void issueCertificate(IssuerAndSubjectData issuerAndSubjectData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {

        if (this.keyStoreDataService.doesKeyStoreExist(issuerAndSubjectData.getCertificateRole().toString())) {
            try {
                System.out.println("Nasao fajl");
                KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
                keyStore.load(new FileInputStream("keystores/" + issuerAndSubjectData.getCertificateRole().toString().toLowerCase() + ".jks"), keyStorePassword.toCharArray());
            } catch (IOException e) {
                System.out.println("Pogresna sifra odmah");
                throw new KeyStoreException();
            }
        }

        if (issuerAndSubjectDataRepository.findByEmail(issuerAndSubjectData.getEmailSubject()) != null) {
            System.out.println("Ima mail");
            throw new NonUniqueResultException();
        }

        Date startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);

        Date endDate;

        if(issuerAndSubjectData.getCertificateRole().equals(CertificateRole.SELF_SIGNED)) {
            c.add(Calendar.YEAR, 30);
            endDate = c.getTime();
        } else if (issuerAndSubjectData.getCertificateRole().equals(CertificateRole.INTERMEDIATE)) {
            c.add(Calendar.YEAR, 20);
            endDate = c.getTime();
        } else {
            c.add(Calendar.YEAR, 10);
            endDate = c.getTime();
        }

        Long issuerId;
        Long subjectId;

        if (!issuerAndSubjectData.getCertificateRole().equals(CertificateRole.SELF_SIGNED)) {
            IssuerAndSubjectData subjectDataToDB = new IssuerAndSubjectData(issuerAndSubjectData.getFirstNameSubject(), issuerAndSubjectData.getLastNameSubject(),
                    issuerAndSubjectData.getOrganizationSubject(), issuerAndSubjectData.getOrganizationUnitSubject(), issuerAndSubjectData.getCountrySubject(),
                    issuerAndSubjectData.getCitySubject(), issuerAndSubjectData.getEmailSubject(), issuerAndSubjectData.getPhoneSubject(), issuerAndSubjectData.getTypeOfEntity(),
                    issuerAndSubjectData.getCertificateRole());
                    Long parentId = issuerAndSubjectDataRepository.findByEmail(issuerAndSubjectData.getEmail()).getId();
                    subjectDataToDB.setParent(parentId);
                    subjectDataToDB.setStartDate(startDate);
                    subjectDataToDB.setEndDate(endDate);
            issuerAndSubjectDataRepository.save(subjectDataToDB);
        } else {
            IssuerAndSubjectData issuerDataToDB = new IssuerAndSubjectData(issuerAndSubjectData.getFirstName(), issuerAndSubjectData.getLastName(),
                    issuerAndSubjectData.getOrganization(), issuerAndSubjectData.getOrganizationUnit(), issuerAndSubjectData.getCountry(),
                    issuerAndSubjectData.getCity(), issuerAndSubjectData.getEmail(), issuerAndSubjectData.getPhone(), issuerAndSubjectData.getTypeOfEntity(),
                    issuerAndSubjectData.getCertificateRole());
                    issuerDataToDB.setStartDate(startDate);
                    issuerDataToDB.setEndDate(endDate);
            issuerAndSubjectDataRepository.save(issuerDataToDB);
        }

        issuerId = issuerAndSubjectDataRepository.findByEmail(issuerAndSubjectData.getEmail()).getId();
        if (issuerAndSubjectData.getCertificateRole().equals(CertificateRole.SELF_SIGNED)) {
            subjectId = issuerId;
        } else {
            subjectId = issuerAndSubjectDataRepository.findByEmail(issuerAndSubjectData.getEmailSubject()).getId();
        }

        KeyPair keyPairIssuer = generators.generateKeyPair();

        SubjectData subjectData = generators.generateSubjectData(subjectId, issuerAndSubjectData.getFirstNameSubject(), issuerAndSubjectData.getLastNameSubject(),
                issuerAndSubjectData.getOrganizationSubject(), issuerAndSubjectData.getOrganizationUnitSubject(), issuerAndSubjectData.getCountrySubject(),
                issuerAndSubjectData.getCitySubject(), issuerAndSubjectData.getEmailSubject(), issuerAndSubjectData.getPhoneSubject(), startDate, endDate);


        IssuerData issuerData = generators.generateIssuerData(issuerId, keyPairIssuer.getPrivate(), issuerAndSubjectData.getFirstName(), issuerAndSubjectData.getLastName(),
                issuerAndSubjectData.getOrganization(), issuerAndSubjectData.getOrganizationUnit(), issuerAndSubjectData.getCountry(),
                issuerAndSubjectData.getCity(), issuerAndSubjectData.getEmail(), issuerAndSubjectData.getPhone());


        X509Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData);

        saveCertificate(issuerAndSubjectData.getCertificateRole(), "sifra", certificate.getSerialNumber().toString(), keyStorePassword, keyPairIssuer.getPrivate(), certificate);

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");

    }

    public void saveCertificate(CertificateRole role, String keyPassword, String alias, String keyStorePassword, PrivateKey privateKey, X509Certificate certificate) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        String type = role.toString().toLowerCase();
        String file = ("keystores/" + type + ".jks");
        KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");

        try {
            keyStore.load(new FileInputStream(file), keyStorePassword.toCharArray());
        } catch (FileNotFoundException e) {
            System.out.println("nisam ga nasao");
            createKeyStore(type, keyStorePassword, keyStore);
        } catch (IOException e) {
            System.out.println("Pogresna lozinka");
        } catch (NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        System.out.println("ovde pise koliko ima pre cuvanja " + keyStore.size());
        keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), new Certificate[]{certificate}); //save cert
        System.out.println("ovde pise koliko ima posle cuvanja " + keyStore.size());
        keyStore.store(new FileOutputStream(file), keyStorePassword.toCharArray());
    }

    private void createKeyStore(String type, String keyStorePassword, KeyStore keyStore) {
        String file = ("keystores/" + type + ".jks");
        try {
            keyStore.load(null, keyStorePassword.toCharArray());
            keyStore.store(new FileOutputStream(file), keyStorePassword.toCharArray());
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String checkCertificateStatus(Long id) {
        return this.issuerAndSubjectDataRepository.getOne(id).getCertificateStatus().toString();
    }

}
