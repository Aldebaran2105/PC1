package com.example.pc1.auth.application;

import com.example.pc1.auth.domain.AuthService;
import com.example.pc1.auth.dto.JwtAuthResponse;
import com.example.pc1.auth.dto.LoginDTO;
import com.example.pc1.auth.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO logInDTO){
        JwtAuthResponse jwtAuthResponseDTO = authenticationService.login(logInDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtAuthResponseDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody RegisterDTO registerDTO){
        JwtAuthResponse jwtAuthResponseDTO = authenticationService.signIn(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtAuthResponseDTO);
    }
}

