package com.bsep.service;

import com.bsep.model.Administrator;

public interface AdministratorService{
    Administrator findByUsername(String username);
}
