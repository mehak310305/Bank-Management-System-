package com.bank.bankapp.dto;
 import lombok.Data;
@Data
public class AddRequestUserDto {
     private String name;
    private String email;
    private String password;
    private String accountNumber;
    private double balance;
}
