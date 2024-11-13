package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IAccountMapper;
import com.bidkoi.auctionkoi.payload.request.*;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.enums.Role;
import com.bidkoi.auctionkoi.repository.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;



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
    IBreederRepository breederRepo;
    IStaffRepository staffRepo;
    PasswordEncoder passwordEncoder;

    IWalletRepository wallerRepo;
    TokenService tokenService;
    EmailService emailService;

    @Override
    public AccountDTO register(RegisterRequest request) {
        if (iAccountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        else if(iAccountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }else if(iAccountRepository.existsByPhone(request.getPhone())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        Account account = iAccountMapper.toAccount(request);
        account.setPassword(this.passwordEncoder.encode(request.getPassword()));
        account.setRole(Role.BIDDER);
        account = iAccountRepository.save(account);

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setReceiver(account);
        emailDetail.setSubject("Welcome to BidKoi");
        emailDetail.setLink("http://localhost:5173/login");
        emailService.sendEmail(emailDetail);



        Bidder bidder = new Bidder();
        bidder.setAccount(account);
        bidder = iBidderRepository.save(bidder);

        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallerRepo.save(wallet);



        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }

    @Override
    public List<Account> getAllAccounts() {
        return iAccountRepository.findAll();
    }

    @Override
    public AccountDTO createAccount(AccountCreationRequest request) {
        String role = request.getRole().toUpperCase();

        // Validate role
        if (!Role.BREEDER.name().equals(role) && !Role.STAFF.name().equals(role)) {
            throw new AppException(ErrorCode.ROLE_ERROR);
        }

        // Check if username already exists
        if (iAccountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Map request to Account entity and encode the password
        Account account = iAccountMapper.toAccountCreation(request);
        account.setPassword(this.passwordEncoder.encode(request.getPassword()));

        // Save account
        iAccountRepository.save(account);

        // Set the role and create the appropriate role entity
        if(Role.BREEDER.name().equals(role)){
            account.setRole(Role.BREEDER);
            Breeder breeder = new Breeder();
            breeder.setAccount(account);
            breederRepo.save(breeder);


            Wallet wallet = new Wallet();
            wallet.setAccount(account);
            wallerRepo.save(wallet);
        }else{
            account.setRole(Role.STAFF);
            Staff staff = new Staff();
            staff.setAccount(account);
            staffRepo.save(staff);
        }

        return iAccountMapper.toAccountDTO(iAccountRepository.save(account));
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        var user = iAccountRepository.findByUsername(request.getUsername())

                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED_USERNAME));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_PASSWORD);
        }

        var token = tokenService.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .bidder(iBidderRepository.findBidderByAccount(user))
                .breeder(breederRepo.findBreederByAccount(user))
                .staff(staffRepo.findByAccount(user))
                .build();
    }

    @Override
    public List<Bidder> getAll() {
        return iBidderRepository.findAll();
    }


    @Override
    public Optional<Account> getAccountById(String id) {
        return iAccountRepository.findById(id);
    }

    @Override
    public Optional<Bidder> getBidderById(String accountId) {
        Account account = iAccountRepository.findById(accountId).
                orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        return iBidderRepository.findByAccountId(accountId);
    }

    @Override
    public Integer numberOfBidder() {
        int count = 0;
        List<Account> accounts = iAccountRepository.findAllByRole(Role.BIDDER);
        for (Account account : accounts) {
            count++;
        }
        return count;
    }

    @Override
    public Integer numberOfBreeder() {
        int count = 0;
        List<Account> accounts = iAccountRepository.findAllByRole(Role.BREEDER);
        for (Account account : accounts) {
            count++;
        }
        return count;
    }

    @Override
    public Integer numberOfStaff() {
        int count = 0;
        List<Account> accounts = iAccountRepository.findAllByRole(Role.STAFF);
        for (Account account : accounts) {
            count++;
        }
        return count;
    }

    @Override
    public void bannedUser(String accountId) {
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        account.setRole(Role.BANNED);
        iAccountRepository.save(account);
    }

    @Override
    public BidderDTO updateProfile(String accountId, BidderDTO bidderDTO) {
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        Bidder bidder = iBidderRepository.findByAccountId(account.getId())
                .orElse(new Bidder());  // Nếu không tìm thấy, tạo mới một Bidder

        if(iAccountRepository.existsByEmailAndIdIsNot(bidderDTO.getEmail(),accountId)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }else if(iAccountRepository.existsByPhoneAndIdIsNot(bidderDTO.getPhone(),accountId)){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }


        // Cập nhật thông tin trong Bidder
        bidder.setAvatar(bidderDTO.getAvatar());
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
                .avatar(bidder.getAvatar())
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


    public Account getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return iAccountRepository.findAccountById(account.getId());
    }

    @Override
    public Account updateFCM(UpdateFCMRequest updateFCMRequest) {
        Account account = getCurrentAccount();
        account.setFcmToken(updateFCMRequest.getFcmToken());
        return iAccountRepository.save(account);
    }

}
