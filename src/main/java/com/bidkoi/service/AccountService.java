package com.bidkoi.service;

import com.bidkoi.dto.AccountDTO;
import com.bidkoi.dto.BidderDTO;
import com.bidkoi.exception.AppException;
import com.bidkoi.exception.ErrorCode;
import com.bidkoi.mapper.IAccountMapper;
import com.bidkoi.payload.request.AccountCreationRequest;
import com.bidkoi.payload.request.LoginRequest;
import com.bidkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.payload.response.LoginResponse;
import com.bidkoi.pojo.Account;
import com.bidkoi.pojo.Bidder;
import com.bidkoi.repository.IAccountRepository;
import com.bidkoi.repository.IBidderRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountService implements IAccountService {

    IAccountRepository iAccountRepository;
    IBidderRepository iBidderRepository;
    IAccountMapper iAccountMapper;
    PasswordEncoder passwordEncoder;
    IEmailService iEmailService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AccountDTO createAccount(AccountCreationRequest request) {
        if (iAccountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        else if(iAccountRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        Account account = iAccountMapper.toAccount(request);
        account.setPassword(this.passwordEncoder.encode(request.getPassword()));

        //Verify user code email
//        var token = generateToken(String.valueOf(account));
//        iEmailService.send(account.getUsername(), account.getEmail(), "Thank you for creating an account. Please verify your email!", token);

        account = iAccountRepository.save(account);

        Bidder bidder = new Bidder();
        bidder.setAccount(account);  // Associate Bidder with the saved Account
        bidder.setEmail(account.getEmail());
        bidder.setPhone(account.getPhone());
        bidder = iBidderRepository.save(bidder);

        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var user = iAccountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUsername());
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .success(true)
                .build();
    }

    @Override
    public Optional<Account> getAccountById(String id) {
        return iAccountRepository.findById(id);
    }

    @Override
    public Optional<Bidder> getBidderById(String accountId) {
        return iBidderRepository.findByAccountId(accountId);
    }


    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("BidKoi.com")
                .issueTime(new Date())
                .expirationTime(new Date(
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
