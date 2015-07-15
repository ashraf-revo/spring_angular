package org.revo.spring_angular.service;

import org.revo.spring_angular.domain.user;

import java.util.Optional;

/**
 * Created by ashraf on 6/7/15.
 */
public interface userService {

    public Optional<user> findByEmail(String email);

    public String getCurrentLogin();
}
