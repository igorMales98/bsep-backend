package com.bsep.repository;

import com.bsep.model.KeyStoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyStoreDataRepository extends JpaRepository<KeyStoreData, Long> {
    KeyStoreData findOneByName(String name);
}
