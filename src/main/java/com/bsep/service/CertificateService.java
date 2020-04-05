package com.bsep.service;

import com.bsep.model.IssuerAndSubjectData;

public interface CertificateService {
    void issueCertificate(IssuerAndSubjectData issuerAndSubjectData);
}
