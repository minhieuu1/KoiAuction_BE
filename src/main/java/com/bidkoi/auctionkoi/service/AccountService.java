package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.mapper.IAccountMapper;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountService implements IAccountService {

    IAccountRepository iAccountRepository;
    IAccountMapper iAccountMapper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AccountDTO createAccount(AccountCreationRequest request) {


        Account account = iAccountMapper.toAccount(request);

        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var user = iAccountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var token = generateToken(request.getUsername());
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }



    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("BidKoi.com")
                .issueTime(new Date())
                .issueTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }
}
