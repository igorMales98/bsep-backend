package com.bsep.service.impl;

import com.bsep.model.Administrator;
import com.bsep.model.Authority;
import com.bsep.model.UserTokenState;
import com.bsep.repository.AuthorityRepository;
import com.bsep.security.TokenUtils;
import com.bsep.security.auth.JwtAuthenticationRequest;
import com.bsep.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;


    @Override
    public Set<Authority> findByName(String name) {
        Authority authority = this.authorityRepository.findByName(name);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public Set<Authority> findById(Long id) {
        Authority authority = this.authorityRepository.getOne(id);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public UserTokenState login(JwtAuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Administrator administrator = (Administrator) authentication.getPrincipal();
        String token = tokenUtils.generateToken(administrator.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return new UserTokenState(token, expiresIn);
    }
}
