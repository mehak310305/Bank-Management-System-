package com.bank.bankapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankapp.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
     
Optional <Account> findByUserId(Long userId);
Optional <Account> findByUserEmail(String email);
Optional <Account> findByAccountNumber(String accountNumber);


}
