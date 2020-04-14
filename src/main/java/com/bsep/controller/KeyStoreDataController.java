package com.bsep.controller;

import com.bsep.dto.DownloadCertificateDTO;
import com.bsep.model.KeyStoreData;
import com.bsep.service.KeyStoreDataService;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/keyStoreData")
public class KeyStoreDataController {

    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @GetMapping(value = "/doesKeyStoreExist/{certificateRole}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> doesKeyStoreExist(@PathVariable("certificateRole") String certificateRole) {
        return new ResponseEntity<>(this.keyStoreDataService.doesKeyStoreExist(certificateRole), HttpStatus.OK);
    }

    @GetMapping(value = "/loadCertificate/{role}/{alias}/{password}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> loadCertificate(@PathVariable("role") String role, @PathVariable("alias") String alias, @PathVariable("password") String password) {
        try {

            X509Certificate certificate = this.keyStoreDataService.loadCertificate(role, alias, password);
            if (certificate == null) {
                return new ResponseEntity<>("Certificate with this alias doesn't exist.", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(certificate.getSubjectDN(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Password is incorrect!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/download")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> download(@RequestBody DownloadCertificateDTO downloadCertificateDTO) {
        try {
            keyStoreDataService.download(downloadCertificateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
