package org.revo.spring_angular.service.impl;

import java.util.stream.Collectors;

import org.revo.spring_angular.repositery.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by ashraf on 6/7/15.
 */
@Service
@Transactional
public class MyUserDetails implements UserDetailsService {

    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).
                map(user -> new User(user.getEmail(), user.getPassword(), user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r)).
                        collect(Collectors.toSet()))).
                orElseThrow(() -> new UsernameNotFoundException("not found "));
    }
}
