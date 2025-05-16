package com.example.pc1.auth.domain;

import com.example.pc1.auth.dto.JwtAuthResponse;
import com.example.pc1.auth.dto.LoginDTO;
import com.example.pc1.config.JwtService;
import com.example.pc1.exceptions.PasswordIncorrectException;
import com.example.pc1.exceptions.ResourceNotFoundException;
import com.example.pc1.exceptions.UserAlreadyExistsException;
import com.example.pc1.user.domain.User;
import com.example.pc1.user.infrastructure.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BaseUserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public JwtAuthResponse login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist or incorrect email "));
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new PasswordIncorrectException("Your password is incorrect");
        }
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generatedToken(user));
        return response;
    }

    public JwtAuthResponse signIn(SignInDTO signInDTO){
        if(userRepository.findByEmail(signInDTO.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setName(signInDTO.getName());
        user.setLastname(signInDTO.getLastname());
        user.setEmail(signInDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signInDTO.getPassword()));
        user.setPhoneNumber(signInDTO.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setRol(Rol.ASSISTANT);
        userRepository.save(user);

        JwtAuthResponse responseDTO = new JwtAuthResponse();
        responseDTO.setToken(jwtService.generateToken(user));
        return responseDTO;
    }

}
