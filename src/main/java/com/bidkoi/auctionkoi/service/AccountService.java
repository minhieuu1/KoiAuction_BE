package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IAccountMapper;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.request.RegisterRequest;
import com.bidkoi.auctionkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.enums.Role;
import com.bidkoi.auctionkoi.pojo.Wallet;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.bidkoi.auctionkoi.repository.IBidderRepository;
import com.bidkoi.auctionkoi.repository.IWalletRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountService implements IAccountService {

    IAccountRepository iAccountRepository;
    IAccountMapper iAccountMapper;
    IBidderRepository iBidderRepository;
    PasswordEncoder passwordEncoder;
    IWalletRepository iWalletRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AccountDTO register(RegisterRequest request) {
        if (iAccountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        else if(iAccountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        Account account = iAccountMapper.toAccount(request);
        account.setPassword(this.passwordEncoder.encode(request.getPassword()));
//        account.setRole(Role.fromValue(request.getRole()));
        account = iAccountRepository.save(account);



        Bidder bidder = new Bidder();
        bidder.setAccount(account);
        bidder = iBidderRepository.save(bidder);

        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        iWalletRepository.save(wallet);

        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }

    @Override
    public AccountDTO createAccount(AccountCreationRequest request) {
        if (iAccountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Account account = iAccountMapper.toAccountCreation(request);
        account.setPassword(this.passwordEncoder.encode(request.getPassword()));
        account.setRole(Role.fromValue(request.getRole()));
        account = iAccountRepository.save(account);


        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }

//    @Override
//    public AccountDTO createAccount(AccountCreationRequest request,int role) {
//        Account account = iAccountMapper.toAccount(request);
//        account.setPassword(this.passwordEncoder.encode(request.getPassword()));
//        return null;
//    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var user = iAccountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_PASSWORD);
        }

        var token = generateToken(user.getId(), user.getUsername(), user.getEmail(), user.getPhone());
        return LoginResponse.builder()
                .token(token)
                .role(user.getRole())
                .build();
    }

    @Override
    public List<Bidder> getAll() {
        return iBidderRepository.findAll();
    }


    String generateToken(String accountId, String username, String email, String phone) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountId)
                .issuer("BidKoi.com")
                .issueTime(new Date())
                .issueTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("username", username)
                .claim("email", email)
                .claim("phone", phone)
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

    @Override
    public Optional<Account> getAccountById(String id) {
        return iAccountRepository.findById(id);
    }

    @Override
    public Optional<Bidder> getBidderById(String accountId) {
//        Account account = iAccountRepository.findById(accountId).
//                orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        return iBidderRepository.findByAccountId(accountId);
    }

    @Override
    public BidderDTO updateProfile(String accountId, BidderDTO bidderDTO) {
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Bidder bidder = iBidderRepository.findByAccountId(account.getId())
                .orElse(new Bidder());  // Nếu không tìm thấy, tạo mới một Bidder



        // Cập nhật thông tin trong Bidder
        bidder.setFirstname(bidderDTO.getFirstname());
        bidder.setLastname(bidderDTO.getLastname());
        bidder.setGender(bidderDTO.getGender());
        bidder.setBirthday(bidderDTO.getBirthday());
        bidder.setAddress(bidderDTO.getAddress());


        account.setEmail(bidderDTO.getEmail());
        account.setPhone(bidderDTO.getPhone());

        bidder.setAccount(account); // Liên kết lại với Account

        iAccountRepository.save(account);
        iBidderRepository.save(bidder);

        BidderDTO updatedBidderDTO = BidderDTO.builder()
                .firstname(bidder.getFirstname())
                .lastname(bidder.getLastname())
                .gender(bidder.getGender())
                .email(account.getEmail())
                .phone(account.getPhone())
                .birthday(bidder.getBirthday())
                .address(bidder.getAddress())
                .build();

        return updatedBidderDTO;
    }

    @Override
    public void updatePassword(String accountId, UpdatePasswordRequest updatePasswordRequest) {

        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        if (!passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }
        // Cập nhật password
        account.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        iAccountRepository.save(account);
    }


}
