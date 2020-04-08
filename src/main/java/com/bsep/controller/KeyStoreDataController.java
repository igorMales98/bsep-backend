package com.bsep.controller;

import com.bsep.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/keyStoreData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class KeyStoreDataController {
    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @PostMapping(value = "/setPassword/{role}/{password}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setPassword(@PathVariable("role") String role, @PathVariable("password") String password) {
        try {
            this.keyStoreDataService.setPassword(role,password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
