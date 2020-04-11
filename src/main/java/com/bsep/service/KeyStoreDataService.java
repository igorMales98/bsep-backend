package com.bsep.service;

import com.bsep.certificate.CertificateRole;
import com.bsep.model.KeyStoreData;

import java.security.cert.Certificate;

public interface KeyStoreDataService {
    void setPassword(KeyStoreData keyStoreData);
    boolean load(String certificateRole);
    boolean checkPassword(KeyStoreData keyStoreData);
}
