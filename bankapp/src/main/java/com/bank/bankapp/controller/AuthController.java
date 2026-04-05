package com.bank.bankapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bankapp.dto.LoginRequestDto;
import com.bank.bankapp.dto.LoginResponseDto;
import com.bank.bankapp.service.AuthService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  @PostMapping("/login")
  public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto)  {
    return authService.login(loginRequestDto);
  }
}
