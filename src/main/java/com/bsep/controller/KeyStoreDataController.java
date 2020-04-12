package com.bsep.controller;

import com.bsep.model.KeyStoreData;
import com.bsep.service.KeyStoreDataService;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/setPassword")
    //  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setPassword(@RequestBody KeyStoreData keyStoreData) {
        try {
            this.keyStoreDataService.setPassword(keyStoreData);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/doesKeyStoreExist/{certificateRole}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> doesKeyStoreExist(@PathVariable("certificateRole") String certificateRole) {
        return new ResponseEntity<>(this.keyStoreDataService.load(certificateRole), HttpStatus.OK);
    }

    @GetMapping(value = "/checkPassword/{name}/{password}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkPassword(@PathVariable("name") String name, @PathVariable("password") String password) {
        return new ResponseEntity<>(this.keyStoreDataService.checkPassword(new KeyStoreData(name, password)), HttpStatus.OK);
    }

    @GetMapping(value = "/loadCertificate/{role}/{alias}/{password}")
    public ResponseEntity<?> loadCertificate(@PathVariable("role")String role, @PathVariable("alias") String alias, @PathVariable("password") String password) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            String keyStoreFile = "keystores/" + role.toLowerCase() + ".jks";
            ks.load(new FileInputStream(keyStoreFile), password.toCharArray());

            System.out.println("velicina key stora" + ks.size());
            System.out.println("Ovde je dosao znaci ima fajla. i ovo je alias " + alias);
            if(ks.containsAlias(alias)) {
                System.out.println("Stampa ako postoji sa alijasom");
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                return new ResponseEntity<>(cert.getIssuerX500Principal(), HttpStatus.OK);
            }
        } catch (KeyStoreException | NoSuchProviderException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
