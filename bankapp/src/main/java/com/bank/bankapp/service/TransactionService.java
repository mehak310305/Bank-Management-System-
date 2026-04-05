package com.bank.bankapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bank.bankapp.dto.SimpleTransactionRequest;
import com.bank.bankapp.dto.TransactionRequestDto;
import com.bank.bankapp.dto.TransferResponseDto;
import com.bank.bankapp.entity.Account;
import com.bank.bankapp.entity.Transaction;
import com.bank.bankapp.entity.User;
import com.bank.bankapp.repository.AccountRepository;
import com.bank.bankapp.repository.TransactionRepository;
import com.bank.bankapp.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class TransactionService {
    
      private final TransactionRepository transactionRepository;
      private final AccountRepository accountRepository;
      private final UserRepository userRepository;

      //depost
      @Transactional
      public String deposit(SimpleTransactionRequest request){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        Account account=accountRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("account not found"));
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        createTransaction(account,null,account,request.getAmount(),Transaction.TransactionType.DEPOSIT);
        log.info("Deposit of Rs.{} successful for user:{}",request.getAmount(),email);
        return "Depsoit Success!New Balance:Rs."+account. getBalance();
      
}
        // 2. WITHDRAWAL
    @Transactional
    public String withdraw(SimpleTransactionRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        
        Account account = accountRepository.findByUserId(user.getId()).orElseThrow();

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException(" Insufficient funds! Available: ₹" + account.getBalance());
        }

        BigDecimal oldBalance = account.getBalance();
        account.setBalance(oldBalance.subtract(request.getAmount()));
        accountRepository.save(account);

        createTransaction(account,account, null, request.getAmount(), Transaction.TransactionType.WITHDRAWAL);

        return " Withdrawal Success!\nOld Balance:"+oldBalance+" | New Balance: "+account.getBalance();
    }

    // 3. TRANSFER    @Transactional
    public String transfer(TransactionRequestDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User fromUser = userRepository.findByEmail(email).orElseThrow();
        
        Account fromAccount = accountRepository.findByUserId(fromUser.getId()).orElseThrow();
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found!"));

        if (fromAccount.getAccountNumber().equals(request.getToAccountNumber())) {
            throw new RuntimeException("Cannot transfer to yourself!");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException(" Insufficient funds for transfer!");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        createTransaction(fromAccount,fromAccount, toAccount, request.getAmount(), Transaction.TransactionType.TRANSFER);

        return "Transfer of ₹" + request.getAmount() + " to " + request.getToAccountNumber() + " successful!";
    }

    // Helper Method to Save Transaction History
    private void createTransaction(Account account ,Account from, Account to, BigDecimal amt, Transaction.TransactionType type) {
        Transaction tx = new Transaction();
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setAmount(amt);
        tx.setType(type);
  tx.setAccount(account);
        tx.setUser(account.getUser());
        tx.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(tx);
    }
     // for see transaction history 
    public List<TransferResponseDto> getTransactions(int page,int size){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User Not Found"));
        
        Account account=user.getAccount();
        List<Transaction> allTransactions=transactionRepository.findByAccountOrderByCreatedAtDesc(account);
        return allTransactions.stream()
        .skip(Math.max(0,(page-1)*size))
        .limit(size)
        .map(t->{TransferResponseDto res=new TransferResponseDto();
            res.setId(t.getId());
            res.setBalance(t.getAmount());
          res.setTransactiontype(t.getType().name());
          res.setTimestamp(t.getCreatedAt());
          if(t.getFromAccount()!=null){
             res.setFromAccount(t.getFromAccount().getAccountNumber());
          }
          if(t.getToAccount()!=null){
             res.setToAccount(t.getToAccount().getAccountNumber());
          }
         return res;
        })
        .collect(Collectors.toList());
    }
}
    
