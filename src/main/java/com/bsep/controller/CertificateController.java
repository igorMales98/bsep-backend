package com.bsep.controller;

import com.bsep.certificate.CertificateStatus;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "/issueCertificate/{keyStorePassword}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> issueCertificate(@RequestBody IssuerAndSubjectData issuerAndSubjectData, @PathVariable("keyStorePassword") String keyStorePassword) {
        try {
            this.certificateService.issueCertificate(issuerAndSubjectData, keyStorePassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NoSuchAlgorithmException | CertificateException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            return new ResponseEntity<>("Password is incorrect! Please try again.", HttpStatus.BAD_REQUEST);
        } catch (NonUniqueResultException e) {
            System.out.println("ovde mora da udje kad je mail isti");
            return new ResponseEntity<>("Certificate with this email already exists. Enter another one.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/getCertificateStatus/{certificateEmail:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCertificateStatus(@PathVariable("certificateEmail") String certificateEmail){
        try {
            CertificateStatus certificateStatus = this.certificateService.getCertificateStatus(certificateEmail);
            return new ResponseEntity<>(certificateStatus.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
