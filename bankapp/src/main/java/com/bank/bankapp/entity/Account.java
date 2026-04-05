package com.bank.bankapp.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)

    @Column(name="account_id")
    private long id;
    private String accountNumber;
    private BigDecimal balance=BigDecimal.ZERO;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy="account",cascade=CascadeType.ALL)
    private List<Transaction> transaction;


}
