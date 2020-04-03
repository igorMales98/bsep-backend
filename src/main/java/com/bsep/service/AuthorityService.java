package com.bsep.service;

import com.bsep.model.Authority;
import com.bsep.model.UserTokenState;
import com.bsep.security.auth.JwtAuthenticationRequest;

import java.util.Set;

public interface AuthorityService {
    Set<Authority> findByName(String name);

    Set<Authority> findById(Long id);

    UserTokenState login(JwtAuthenticationRequest authenticationRequest);
}
