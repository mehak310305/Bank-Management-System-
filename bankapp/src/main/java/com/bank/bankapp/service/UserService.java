package com.bank.bankapp.service;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bank.bankapp.dto.AddRequestUserDto;
import com.bank.bankapp.dto.DashboardResponseDto;
import com.bank.bankapp.dto.PasswordChangeRequestDto;
import com.bank.bankapp.dto.TransferResponseDto;
// import com.bank.bankapp.dto.DashboardDto;
import com.bank.bankapp.dto.UserDto;
import com.bank.bankapp.dto.UserprofileDto;
import com.bank.bankapp.entity.Account;
import com.bank.bankapp.entity.User;
import com.bank.bankapp.repository.AccountRepository;
import com.bank.bankapp.repository.UserRepository;

@Service 
public class UserService {
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
      @Autowired
    private TransactionService transactionService;

    public UserDto registerUser(AddRequestUserDto addRequestUserDto) {
        User user = modelMapper.map(addRequestUserDto,User.class);
        user.setPassword(passwordEncoder.encode(addRequestUserDto.getPassword()));
        User savedUser= userRepository.save(user);
        //auto account creation
        Account account=new Account();
        String generatedAccountNumber="ACC"+System.currentTimeMillis();
        account.setAccountNumber(generatedAccountNumber) ;
        account.setBalance(BigDecimal.ZERO);
        account.setUser(savedUser);
        accountRepository.save(account);
         
        UserDto userDto=modelMapper.map(savedUser,UserDto.class);
        userDto.setAccountNumber(generatedAccountNumber);
        userDto.setBalance(BigDecimal.ZERO);
        return userDto;
    }
    // BALANCE 
    public BigDecimal getBalance()
    {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException ("User not found"));
        return user.getAccount().getBalance();
    }
     // service logic for profile mangement
     public UserprofileDto getUserProfile(){
        // GET EMAIL FROM SECURITY CONTEXT 
    String email=SecurityContextHolder.getContext().getAuthentication().getName();
  User user=userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
    Account account=user.getAccount();
    // eturn the mapped Dto
    return new UserprofileDto(
     user.getName(),
    user.getEmail(),
    account.getAccountNumber(),
    account.getBalance()
      
      ); }

      // update password
      public String changePassword(PasswordChangeRequestDto request){
         String email=SecurityContextHolder.getContext().getAuthentication().getName();
  User user=userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
   //verify old password
if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword())){
 return "Incorrect old password";
}
// new password hash
user.setPassword(passwordEncoder.encode(request.getNewPassword()));
userRepository.save(user);
return"Password updated successfully";
      }


 // dashboard service
 public DashboardResponseDto getDashboardSummary()
 {
     String email=SecurityContextHolder.getContext().getAuthentication().getName();
  User user=userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
    Account account=user.getAccount();
    //get only 3 transactions
    List<TransferResponseDto> recent = transactionService.getTransactions(0,3);
    return new DashboardResponseDto(
      user.getName(),
      account.getAccountNumber(),
      account.getBalance(),
         recent
 );
 }     
 }


