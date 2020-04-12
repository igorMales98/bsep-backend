package com.bsep.service;

import com.bsep.model.IssuerAndSubjectData;

import java.util.Collection;

public interface IssuerAndSubjectDataService {
    Collection<IssuerAndSubjectData> getSSAndCa();
}
