package com.bsep.controller;

import com.bsep.model.IssuerAndSubjectData;
import com.bsep.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/checkStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkCertificateStatus(@PathVariable("id") Long id){
        try {
            String status = this.certificateService.checkCertificateStatus(id);
            System.out.println(status);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Certificate with that alias doesn't exist!",HttpStatus.BAD_REQUEST);
        }
    }



}
