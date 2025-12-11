package com.online.store.service.auth;

import com.online.store.dto.auth.LoginDto;
import com.online.store.dto.auth.LoginResponseDto;
import com.online.store.security.JwtUtilsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final JwtUtilsService jwtUtilsService;
    private final AuthenticationManager authenticationManager;


    public LoginService(JwtUtilsService jwtUtilsService,
                        AuthenticationManager authenticationManager) {
        this.jwtUtilsService = jwtUtilsService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDto login(LoginDto loginDto){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        String token = jwtUtilsService.generateToken(userDetails);
        return new LoginResponseDto(token);
    }
}
