package com.bsep.controller;

import com.bsep.certificate.CertificateRole;
import com.bsep.model.KeyStoreData;
import com.bsep.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.cert.Certificate;

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
        return new ResponseEntity<>(this.keyStoreDataService.load(certificateRole),HttpStatus.OK);
    }

    @PostMapping(value = "/checkPassword")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkPassword(@RequestBody KeyStoreData keyStoreData) {
        return new ResponseEntity<>(this.keyStoreDataService.checkPassword(keyStoreData),HttpStatus.OK);
    }
}
