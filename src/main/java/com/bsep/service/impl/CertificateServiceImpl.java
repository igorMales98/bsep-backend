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
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

@Service
public class CertificateServiceImpl implements CertificateService {

    private Generators generators = new Generators();

    private CertificateGenerator certificateGenerator = new CertificateGenerator();

    @Autowired
    private IssuerAndSubjectDataRepository issuerAndSubjectDataRepository;

    @Override
    public void issueCertificate(IssuerAndSubjectData issuerAndSubjectData) {

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

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");

    }
}
