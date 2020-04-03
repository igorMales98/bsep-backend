package com.bsep.service.impl;

import com.bsep.model.Administrator;
import com.bsep.repository.AdministratorRepository;
import com.bsep.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService, UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator = findByUsername(username);
        if(administrator == null) {
            throw new UsernameNotFoundException(String.format("User with username '%s' was not found", username));
        } else {
            return administrator;
        }
    }

    @Override
    public Administrator findByUsername(String username) {
        return administratorRepository.findByUsername(username);
    }
}
