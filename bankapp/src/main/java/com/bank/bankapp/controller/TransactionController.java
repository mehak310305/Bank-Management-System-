package com.bank.bankapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bankapp.dto.SimpleTransactionRequest;
import com.bank.bankapp.dto.TransactionRequestDto;
import com.bank.bankapp.dto.TransferResponseDto;
import com.bank.bankapp.entity.Transaction;
import com.bank.bankapp.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor

public class TransactionController {
      
    private final TransactionService transactionService;
    

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody SimpleTransactionRequest  request){
        return ResponseEntity.ok(transactionService.deposit(request));
    }
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody SimpleTransactionRequest  request){
        return ResponseEntity.ok(transactionService.withdraw(request));
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransactionRequestDto  request){
        return ResponseEntity.ok(transactionService.transfer(request));
    }
    
    //for see transaction history 
    @GetMapping("/transactions")
    public List<TransferResponseDto> getTransaction (@RequestParam int page, @RequestParam int size){
        return transactionService.getTransactions(page, size);
    }
}
