package com.bank.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class PasswordChangeRequestDto {
     private String oldPassword;
     private String newPassword;
}
