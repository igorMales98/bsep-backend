package com.bsep.controller;

import com.bsep.model.KeyStoreData;
import com.bsep.service.KeyStoreDataService;
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

    @GetMapping(value = "/load/{certificateRole}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> load(@PathVariable("certificateRole") String certificateRole) {
        System.out.println("Usao u load");
        return new ResponseEntity<>(this.keyStoreDataService.load(certificateRole), HttpStatus.OK);
    }

    @GetMapping(value = "/checkPassword/{name}/{password}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkPassword(@PathVariable("name") String name, @PathVariable("password") String password) {
        return new ResponseEntity<>(this.keyStoreDataService.checkPassword(new KeyStoreData(name, password)), HttpStatus.OK);
    }

    @GetMapping(value = "/loadCertificate/{role}/{alias}/{password}")
    public Certificate loadCertificate(@PathVariable("role")String role, @PathVariable("alias") String alias, @PathVariable("password") String password) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            String keyStoreFile = "keystores/" + role.toLowerCase() + ".jks";
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, password.toCharArray());

            if(ks.isKeyEntry(alias)) {
                Certificate cert = ks.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
