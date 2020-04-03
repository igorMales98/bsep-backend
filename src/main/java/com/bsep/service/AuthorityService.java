package com.bsep.service;

import com.bsep.model.Authority;

import java.util.Set;

public interface AuthorityService {
    Set<Authority> findByName(String name);

    Set<Authority> findById(Long id);
}
