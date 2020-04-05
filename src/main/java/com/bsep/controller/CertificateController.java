package com.bsep.controller;

import com.bsep.model.IssuerAndSubjectData;
import com.bsep.model.IssuerData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

    @PostMapping(value = "/issueCertificate")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IssuerData> issueCertificate(@RequestBody IssuerAndSubjectData issuerAndSubjectData) {
        System.out.println(issuerAndSubjectData.getFirstName() + issuerAndSubjectData.getLastName() + issuerAndSubjectData.getFirstNameSubject() + issuerAndSubjectData.getLastNameSubject());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
