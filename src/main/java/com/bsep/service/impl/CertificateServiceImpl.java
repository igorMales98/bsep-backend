package com.bsep.service.impl;

import com.bsep.certificate.Generators;
import com.bsep.model.IssuerAndSubjectData;
import com.bsep.model.SubjectData;
import com.bsep.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements CertificateService {

    private Generators generators = new Generators();

    @Override
    public void issueCertificate(IssuerAndSubjectData issuerAndSubjectData) {
        // SubjectData subjectData =
    }
}
