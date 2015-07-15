package org.revo.spring_angular.service.impl;

import org.revo.spring_angular.domain.user;
import org.revo.spring_angular.repositery.userRepository;
import org.revo.spring_angular.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by ashraf on 6/7/15.
 */
@Service
@Transactional
public class userServiceImpl implements userService {

    @Autowired
    userRepository Repository;

    @Override
    public Optional<user> findByEmail(String email) {
        return Repository.findByEmail(email);
    }

    public String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
