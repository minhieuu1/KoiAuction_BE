package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    @Autowired
    IAccountRepository  accountRepo;

    @Value("${jwt.signerKey}")
    public String SECRET_KEY;

    private SecretKey getSignerKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // create token
    public String generateToken(Account account) {
        String token = Jwts.builder()
                .subject(account.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignerKey())
                .compact();
        return token;
    }

    //verify token
    public Account getAccountByToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String accountId = claims.getSubject();

        return accountRepo.findAccountById(accountId);
    }
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(account.getId())
//                .issuer("BidKoi.com")
//                .issueTime(new Date(System.currentTimeMillis()))
//                .expirationTime(new Date(
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
//                ))
//                .build();
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//
//        JWSObject jwsObject = new JWSObject(header, payload);
//
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            return jwsObject.serialize();
//        } catch (JOSEException e) {
//            log.error("Cannot create token");
//            throw new RuntimeException(e);
//        }
//    }
}
