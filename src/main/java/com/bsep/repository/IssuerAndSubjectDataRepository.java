package com.bsep.repository;

import com.bsep.model.IssuerAndSubjectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerAndSubjectDataRepository extends JpaRepository<IssuerAndSubjectData, Long> {
}
