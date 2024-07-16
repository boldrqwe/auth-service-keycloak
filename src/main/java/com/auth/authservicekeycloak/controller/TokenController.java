package com.auth.authservicekeycloak.controller;

import com.auth.authservicekeycloak.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/get-token")
    public String getToken(@RequestParam String username, @RequestParam String password) {
        return tokenService.getAccessToken(username, password);
    }

    @GetMapping("/1")
    public String getToken1(){
        return "1";
    }
}

