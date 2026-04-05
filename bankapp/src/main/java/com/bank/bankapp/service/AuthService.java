package com.bank.bankapp.service;
import com.bank.bankapp.dto.LoginRequestDto;
import com.bank.bankapp.dto.LoginResponseDto;
import com.bank.bankapp.entity.User;
import com.bank.bankapp.repository.UserRepository;
import com.bank.bankapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
            String token = jwtUtil.generateAccessToken(loginRequestDto.getEmail());
            return new LoginResponseDto(token, "Login successful!");
       
    }}
