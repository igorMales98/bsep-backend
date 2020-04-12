package com.bsep.repository;

import com.bsep.model.IssuerAndSubjectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IssuerAndSubjectDataRepository extends JpaRepository<IssuerAndSubjectData, Long> {

    @Query(value = "SELECT * FROM issuer_and_subject_data data WHERE data.email = :email", nativeQuery = true)
    IssuerAndSubjectData findByEmail(String email);

    @Query(value = "SELECT * FROM issuer_and_subject_data dataa WHERE dataa.certificate_role = 'SELF_SIGNED' OR dataa.certificate_role = 'INTERMEDIATE'", nativeQuery = true)
    Collection<IssuerAndSubjectData> getSSAndCA();
}
