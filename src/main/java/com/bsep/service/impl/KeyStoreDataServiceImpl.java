package com.bsep.service.impl;

import com.bsep.certificate.CertificateRole;
import com.bsep.model.KeyStoreData;
import com.bsep.repository.KeyStoreDataRepository;
import com.bsep.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

@Service
public class KeyStoreDataServiceImpl implements KeyStoreDataService {

    @Autowired
    private KeyStoreDataRepository keyStoreDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setPassword(KeyStoreData keyStoreData) {
        keyStoreData.setPassword(passwordEncoder.encode(keyStoreData.getPassword()));
        this.keyStoreDataRepository.save(keyStoreData);
    }

    public boolean load(String certificateRole) {
        String name = certificateRole.toLowerCase();
        File file = new File("keystores/" + name + ".jks");
        return file.exists();
    }

    public boolean checkPassword(KeyStoreData keyStoreData) {
        KeyStoreData keyStore = keyStoreDataRepository.findOneByName(keyStoreData.getName());
        return passwordEncoder.encode(keyStoreData.getPassword()).equals(keyStore.getPassword());
    }
}
