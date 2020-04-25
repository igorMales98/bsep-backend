package com.bsep.certificate;

import com.bsep.model.IssuerData;
import com.bsep.model.SubjectData;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.security.*;
import java.util.Calendar;
import java.util.Date;

public class Generators {

    public Generators() {
    }

    public IssuerData generateIssuerData(Long sn, PrivateKey privateKey, String firstName, String lastName, String organization,
                                        String organizationUnit, String country, String city, String email, String phone) {

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);

        this.buildData(builder, firstName, lastName, organization, organizationUnit, country, city, email, phone, sn.toString());

        return new IssuerData(privateKey, builder.build());
    }

    public SubjectData generateSubjectData(Long sn, String firstName, String lastName, String organization, String organizationUnit,
                                           String country, String city, String email, String phone, CertificateRole role) {
        try {
            KeyPair keyPairSubject = generateKeyPair();
            Date startDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);

            Date endDate;

            if(role.equals(CertificateRole.SELF_SIGNED)) {
                c.add(Calendar.YEAR, 30);
                endDate = c.getTime();
            } else if (role.equals(CertificateRole.INTERMEDIATE)) {
                c.add(Calendar.YEAR, 20);
                endDate = c.getTime();
            } else {
                c.add(Calendar.YEAR, 10);
                endDate = c.getTime();
            }

            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            this.buildData(builder, firstName, lastName, organization, organizationUnit, country, city, email, phone, sn.toString());


            return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn.toString(), startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(2048, random);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void buildData(X500NameBuilder builder, String firstName, String lastName, String organization,
                           String organizationUnit, String country, String city, String email, String phone, String sn) {

        builder.addRDN(BCStyle.GIVENNAME, firstName);
        builder.addRDN(BCStyle.SURNAME, lastName);
        builder.addRDN(BCStyle.O, organization);
        builder.addRDN(BCStyle.OU, organizationUnit);
        builder.addRDN(BCStyle.COUNTRY_OF_RESIDENCE, country);
        builder.addRDN(BCStyle.EmailAddress, email);
        builder.addRDN(BCStyle.TELEPHONE_NUMBER, phone);
        builder.addRDN(BCStyle.UID, sn);

    }

}
