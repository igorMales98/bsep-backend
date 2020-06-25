package com.bsep.service.impl;

import com.bsep.dto.DownloadCertificateDTO;
import com.bsep.certificate.CertificateRole;
import com.bsep.certificate.CertificateStatus;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.repository.IssuerAndSubjectDataRepository;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.service.KeyStoreDataService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class KeyStoreDataServiceImpl implements KeyStoreDataService {

    @Autowired
    private IssuerAndSubjectDataRepository issuerAndSubjectDataRepository;


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
    public void withdrawCertificate(String certificateEmail) {
        IssuerAndSubjectData certificateForWithdraw = issuerAndSubjectDataRepository.findByEmail(certificateEmail);
        Long id = certificateForWithdraw.getId();

        certificateForWithdraw.setCertificateStatus(CertificateStatus.REVOKED);
        issuerAndSubjectDataRepository.save(certificateForWithdraw);

        if (certificateForWithdraw.getCertificateRole().equals(CertificateRole.END_ENTITY)) {
            certificateForWithdraw.setCertificateStatus(CertificateStatus.REVOKED);
            issuerAndSubjectDataRepository.save(certificateForWithdraw);
        }

        List<IssuerAndSubjectData> allCertificates = issuerAndSubjectDataRepository.findAll();

        if (certificateForWithdraw.getCertificateRole().equals(CertificateRole.SELF_SIGNED) || certificateForWithdraw.getCertificateRole().equals(CertificateRole.INTERMEDIATE)) {
            for (IssuerAndSubjectData c : allCertificates) {
                IssuerAndSubjectData tempCertificate = issuerAndSubjectDataRepository.findByEmail(c.getEmail());
                if (tempCertificate.getParent() != null) {
                    if (tempCertificate.getParent().equals(id)) {
                        tempCertificate.setCertificateStatus(CertificateStatus.REVOKED);
                        issuerAndSubjectDataRepository.save(c);
                    }
                }
            }
        }


    }

    @Override
    public CertificateStatus getCertificateStatus(String certificateEmail) {
        IssuerAndSubjectData certificate = issuerAndSubjectDataRepository.findByEmail(certificateEmail);
        return certificate.getCertificateStatus();

    }


    @Override
    public void download(DownloadCertificateDTO downloadCertificateDTO) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException, DocumentException {
        X509Certificate certificate = this.loadCertificate(downloadCertificateDTO.getRole(), downloadCertificateDTO.getAlias(),
                downloadCertificateDTO.getKeyStorePassword());
        System.out.println("nabavio je cert");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/data/" + downloadCertificateDTO.getRole().toLowerCase() + "_" + downloadCertificateDTO.getAlias() + ".pdf"));

        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 24, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Sertifikat", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(2);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setPaddingTop(300);
        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
        table.getDefaultCell().setPadding(10);
        addRows(table, certificate);

        document.add(table);
        document.close();
    }

    private void addRows(PdfPTable table, X509Certificate certificate) {
        table.addCell("Podaci o izdavacu sertifikata");
        table.addCell(this.parseDateSoItLooksNice(certificate.getIssuerDN()));
        table.addCell("Podaci o vlasniku sertifikata");
        table.addCell(this.parseDateSoItLooksNice(certificate.getSubjectDN()));
        table.addCell("Vazi od");
        table.addCell(String.valueOf(certificate.getNotBefore()));
        table.addCell("Vazi do");
        table.addCell(String.valueOf(certificate.getNotAfter()));
        table.addCell("Javni kljuc");
        table.addCell(String.valueOf(certificate.getPublicKey()));

    }

    private String parseDateSoItLooksNice(Principal data) {
        String[] fullData = String.valueOf(data).split(",");
        String phone = fullData[1].split("=")[1];
        String email = fullData[2].split("=")[1];
        String country = fullData[3].split("=")[1];
        String organizationUnit = fullData[4].split("=")[1];
        String organization = fullData[5].split("=")[1];
        String lastName = fullData[6].split("=")[1];
        String firstName = fullData[7].split("=")[1];

        return "First name : " + firstName + "\n" +
                "Last name : " + lastName + "\n" +
                "Email : " + email + "\n" +
                "Organization : " + organization + "\n" +
                "Organization Unit: " + organizationUnit + "\n" +
                "Phone : " + phone + "\n" +
                "Country : " + country + "\n";
    }
}
