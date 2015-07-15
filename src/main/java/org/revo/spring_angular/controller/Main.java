package org.revo.spring_angular.controller;

import org.revo.spring_angular.domain.user;
import org.revo.spring_angular.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * Created by ashraf on 6/3/15.
 */
@Controller
@RequestMapping(value = "/")
public class Main {

    @Autowired
    userService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/api/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<user> getAccount() {
        return Optional.ofNullable(userService.getCurrentLogin()).map(data
                -> userService.findByEmail(data).
                map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED)))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

    }
}
