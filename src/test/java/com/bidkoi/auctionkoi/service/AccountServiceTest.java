package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyString;
import  static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceTest {
    @SpyBean
    @Autowired
    AccountService accountService;


    @MockBean
    IAccountRepository accountRepository;
    @MockBean
    PasswordEncoder passwordEncoder;


    Account account;
    LoginRequest loginRequest;

    @BeforeEach
    void initData(){

        loginRequest = LoginRequest.builder()
                .username("minhhieu")
                .password("12345678")
                .build();

        account = Account.builder()
                .id("c41b4591")
                .username("minhhieu")
                .password("1234567890")
                .email("minhhieu@gmail.com")
                .phone("1234567890")
                .build();

    }


//    @Test
//    void login_success(){
//
//        String token = "";
//
//        //GIVEN
//        //Exist user
//        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
//        //Password correct
//        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
//        //Generate Token
//        when(accountService.generateToken(anyString(), anyString(),anyString(), anyString())).thenReturn(token);
//
//        //WHEN
//        var result = accountService.login(loginRequest);
//
//        //THEN
//        assertNotNull(result);
//        assertEquals(token, result.getToken());
//    }

//    @Test
//    void login_invalidUsername(){
//
//        //GIVEN
//        //User not found
//        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
//        //Password correct
//        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
//
//        //WHEN
//        var exception =  assertThrows(AppException.class,
//                () -> accountService.login(loginRequest));
//
//        //THEN: Expect an AppException with the correct error code and message
//        assertEquals(401, exception.getErrorCode().getCode());
//        assertEquals("Invalid username!", exception.getErrorCode().getMessage());
//
//    }
//
//    @Test
//    void login_invalidPassword(){
//
//        //GIVEN
//        //Exist user
//        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
//        //Password incorrect
//        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);
//
//        //WHEN
//        var exception =  assertThrows(AppException.class,
//                () -> accountService.login(loginRequest));
//
//        //THEN: Expect an AppException with the correct error code and message
//        assertEquals(401, exception.getErrorCode().getCode());
//        assertEquals("Invalid password!", exception.getErrorCode().getMessage());
//
//    }
}
