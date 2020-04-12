package com.bsep.controller;

import com.bsep.service.IssuerAndSubjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/issuersAndSubjects")
public class IssuerAndSubjectDataController {

    @Autowired
    private IssuerAndSubjectDataService issuerAndSubjectDataService;

    @GetMapping(value = "/getSSAndCA")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSSAndCa() {
        try {
            return new ResponseEntity<>(this.issuerAndSubjectDataService.getSSAndCa(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
