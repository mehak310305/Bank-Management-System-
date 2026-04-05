package com.bank.bankapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    
    private Long Id;

    @Column(nullable=false)
     private BigDecimal amount=BigDecimal.ZERO;

     @Enumerated(EnumType.STRING)
     @Column(nullable=false)
     private TransactionType type;

     private LocalDateTime createdAt=LocalDateTime.now();

     @ManyToOne
     @JoinColumn(name="account_id")
     private Account account;

     @ManyToOne
     @JoinColumn(name="from_account_id")
     private Account fromAccount;

     @ManyToOne
    @JoinColumn(name="to_account_id")
     private Account toAccount;
     
      @ManyToOne
     @JoinColumn(name="user_id")
     private User user;

     public enum TransactionType{
        DEPOSIT,WITHDRAWAL,TRANSFER
     }

 
     
}
