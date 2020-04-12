package com.bsep.service.impl;

import com.bsep.model.IssuerAndSubjectData;
import com.bsep.repository.IssuerAndSubjectDataRepository;
import com.bsep.service.IssuerAndSubjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IssuerAndSubjectDataServiceImpl implements IssuerAndSubjectDataService {

    @Autowired
    private IssuerAndSubjectDataRepository issuerAndSubjectDataRepository;

    @Override
    public Collection<IssuerAndSubjectData> getSSAndCa() {
        return this.issuerAndSubjectDataRepository.getSSAndCA();
    }
}
