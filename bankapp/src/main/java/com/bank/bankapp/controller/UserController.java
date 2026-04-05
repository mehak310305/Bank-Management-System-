package com.bank.bankapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bankapp.service.UserService;
import com.bank.bankapp.util.PdfGenerator;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

import com.bank.bankapp.dto.AddRequestUserDto;
import com.bank.bankapp.dto.DashboardResponseDto;
import com.bank.bankapp.dto.PasswordChangeRequestDto;
import com.bank.bankapp.dto.UserDto;
import com.bank.bankapp.dto.UserprofileDto;
import com.bank.bankapp.entity.Transaction;
import com.bank.bankapp.entity.User;
import com.bank.bankapp.repository.TransactionRepository;
import com.bank.bankapp.repository.UserRepository;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/users")
public class UserController {
      
@Autowired 
 private UserService userService;

 @Autowired 
 private UserRepository userRepository;

 @Autowired 
 private TransactionRepository transactionRepository;

 
 @PostMapping("/register")
 public UserDto registerUser(@RequestBody AddRequestUserDto addRequestUserDto){
    return userService.registerUser(addRequestUserDto);
 }
 @GetMapping("/balance")
 public ResponseEntity<BigDecimal> getBalance(){
   return ResponseEntity.ok(userService.getBalance());
 }
 // for profile management
  @GetMapping("/profile")
  public ResponseEntity<UserprofileDto> getProfile(){
     return ResponseEntity.ok(userService.getUserProfile());
  }
  // for update password
  @PutMapping("/change_password")

  public ResponseEntity<String> updatePassword(@RequestBody PasswordChangeRequestDto request){
   String response = userService.changePassword(request);
   if(response.contains("successfully")){
      return ResponseEntity.ok(response);
   } 
   return ResponseEntity.badRequest().body(response);
  }
   @GetMapping("/dashboard")
   public ResponseEntity<DashboardResponseDto> getDashboard()
{
   return ResponseEntity.ok(userService.getDashboardSummary());
}


// for download pdf
@GetMapping("/download/statement")
public void downloadPdf(HttpServletResponse response) throws IOException {
    // 1. Browser ko batana ki ye ek PDF file hai
    response.setContentType("application/pdf");
    
    // 2. File ka naam set karna (attachment ka matlab hai download hogi)
    response.setHeader("Content-Disposition", "attachment; filename=bank_statement.pdf");

    // 3. Current User ki email nikalna (Security Context se)
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 4. User ke saare transactions fetch karna (Sorted by Date)
    List<Transaction> transactions = transactionRepository.findByAccountOrderByCreatedAtDesc(user.getAccount());

    // 5. PdfGenerator class ko call karna file likhne ke liye
    PdfGenerator generator = new PdfGenerator();
    generator.generate(transactions, response);
}
}