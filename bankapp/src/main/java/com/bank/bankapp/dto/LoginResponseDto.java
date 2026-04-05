package com.bank.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoginResponseDto {


 private String Jwt;
 private String message; 
}
