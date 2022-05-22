package com.javasampleapproach.springrest.postgresql.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @GetMapping("/restricted")
    public String restricted(){
        return "If you are here, you are logged";
    }


}