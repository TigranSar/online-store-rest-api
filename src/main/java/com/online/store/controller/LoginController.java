package com.online.store.controller;

import com.online.store.dto.auth.LoginDto;
import com.online.store.dto.auth.LoginResponseDto;
import com.online.store.service.auth.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        LoginResponseDto loginResponseDto = loginService.login(loginDto);
        return ResponseEntity.ok(loginResponseDto);
    }
}
