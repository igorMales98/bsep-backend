package com.bsep.service.impl;

import com.bsep.model.KeyStoreData;
import com.bsep.repository.KeyStoreDataRepository;
import com.bsep.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;

@Service
public class KeyStoreDataServiceImpl implements KeyStoreDataService {

    @Autowired
    private KeyStoreDataRepository keyStoreDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setPassword(String role, String password) {
        KeyStoreData keyStoreData = new KeyStoreData();
        keyStoreData.setName(role);
        keyStoreData.setPassword(passwordEncoder.encode(password));
        this.keyStoreDataRepository.save(keyStoreData);
    }
}
