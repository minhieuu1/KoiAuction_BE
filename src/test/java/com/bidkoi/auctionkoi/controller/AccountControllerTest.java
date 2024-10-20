package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.payload.request.RegisterRequest;
import com.bidkoi.auctionkoi.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private RegisterRequest request;
    private AccountDTO response;

    @BeforeEach
    void initData(){
        request = RegisterRequest.builder()
                .username("minhhieu")
                .password("12345678")
                .email("minhhieu@gmail.com")
                .phone("1234567890")
                .build();

        response = AccountDTO.builder()
                .id("c41b4591")
                .username("minhhieu")
                .email("minhhieu@gmail.com")
                .phone("1234567890")
                .build();
    }

    @Test
    void createAccount_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(accountService.register(ArgumentMatchers.any()))
                .thenReturn(response);

        //WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
//                .andExpect(MockMvcResultMatchers.jsonPath("data.username").value("minhhieu"))

        ;

    }
    @Test
    void createAccount_usernameInvalid_fail() throws Exception {
        //GIVEN
        request.setUsername("minh");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        //WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Username must be between 8 and 16 characters"))

        ;

    }
}
