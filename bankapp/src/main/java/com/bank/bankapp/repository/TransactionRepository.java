package com.bank.bankapp.repository;

import java.util.List;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankapp.entity.Account;
import com.bank.bankapp.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    

    // List <Transaction> findByFromAccount_AccountNumberOrToAccount_AccountNumberOrderByCreatedAtDesc(String fromAccountNumber,
    //     String toAccountNumber,Pageable pageable);
 List <Transaction> findByAccountOrderByCreatedAtDesc(Account account);

}
