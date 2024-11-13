package com.bidkoi.auctionkoi.service;



import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.enums.TransactionsEnum;

import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IWalletMapper;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import com.bidkoi.auctionkoi.pojo.Account;

import com.bidkoi.auctionkoi.pojo.Transactions;
import com.bidkoi.auctionkoi.pojo.Wallet;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.bidkoi.auctionkoi.repository.ITransactionsRepository;

import com.bidkoi.auctionkoi.repository.IWalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService implements IWalletService {

    IAccountRepository accountRepo;
    IWalletRepository walletRepo;
    IWalletMapper mapper;
    ITransactionsRepository transactionRepository;

    @Override
    public WalletDTO createWallet(WalletRequest request, String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wallet wallet = walletRepo.findWalletByAccount(account);

//        Transactions transactions = Transactions.builder()
//                .amount(request.getBalance())
//                .description("Nạp tiền vào ví")
//                .type(String.valueOf(TransactionsEnum.DEPOSIT))
//                .date(new Date())
//                .wallet(wallet)
//                .build();

        wallet.setBalance(wallet.getBalance() + request.getBalance());
        //wallet.getTransactions().add(transactions);

        return mapper.toWalletDTO(walletRepo.save(wallet));
    }


    @Override
    public String createUrl(WalletRequest walletRequest, String accountId) throws Exception {
        // Fetch account details
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wallet wallet = walletRepo.findWalletByAccount(account);


        Transactions transactions = Transactions.builder()
                .amount(walletRequest.getBalance())
                .description("Deposit to Wallet")
                .type(TransactionsEnum.ADD_MONEY)
                .status("PENDING")
                .date(LocalDateTime.now())
                .wallet(wallet)
                .build();

        Transactions saveTransaction = transactionRepository.save(transactions);
        String transactionId = saveTransaction.getTransactionId().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String createDate = LocalDateTime.now().format(formatter);

        // Payment parameters
        String tmnCode = "8TLX9C45";
        String secretKey = "OIHP9HPL5O7QYVG4HIZ49LIT1LRG6GAU";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

        // Return URL after payment
        //String returnUrl = "https://blearning.vn/guide/swp/docker-local?walletId=" + wallet.getWalletId(); // Adjust this if necessary


        String returnUrl = "http://localhost:5173/success?transactionId=" + transactionId;
//        String returnUrl = "https://oauth.pstmn.io/v1/browser-callback?transactionId=" + transactionId;

        String currCode = "VND";

        // Create payment parameters map
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef",  transactionId);
        vnpParams.put("vnp_OrderInfo", "Nạp Tiền Vào Ví: " + accountId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", String.valueOf((int) (walletRequest.getBalance() * 100))); // Convert to integer for VNPay


        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", createDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        // Create signature
        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove the last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);
        vnpParams.put("vnp_SecureHash", signed);

        // Create payment URL
        StringBuilder paymentUrl = new StringBuilder(vnpUrl);
        paymentUrl.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            paymentUrl.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            paymentUrl.append("=");
            paymentUrl.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            paymentUrl.append("&");
        }
        paymentUrl.deleteCharAt(paymentUrl.length() - 1); // Remove the last '&'

        //log.info("Payment URL: {}", paymentUrl.toString());
        return paymentUrl.toString();
    }


    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {

        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec KeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(KeySpec);

        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }



    @Override
    public void handleVnPayCallback(String transactionId, String responseCode, double vnpAmount) {
        // Lấy giao dịch từ ID
        Transactions transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if(transaction.getStatus().equals("COMPLETED")) {
            throw new AppException(ErrorCode.TRANSACTION_COMPLETED);

        }

        Wallet wallet = transaction.getWallet();

        if ("00".equals(responseCode)) {
            // Cập nhật số dư ví và trạng thái giao dịch
            double amount = vnpAmount / 100;  // VNPay gửi số tiền * 100

            wallet.setBalance(wallet.getBalance() + amount);
            walletRepo.save(wallet);  // Lưu ví với số dư mới

            transaction.setStatus("COMPLETED");
        } else {
            // Giao dịch thất bại
            transaction.setStatus("FAILED");

            transaction.setDescription("Fail Deposit to Wallet");

        }

        // Lưu giao dịch với trạng thái mới
        transactionRepository.save(transaction);

    }

    @Override
    public WalletDTO getWallet(String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wallet wallet = walletRepo.findWalletByAccount(account);

        return mapper.toWalletDTO(wallet);
    }

    @Override
    public void deposit(String accountId, WalletRequest request) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wallet wallet = walletRepo.findWalletByAccount(account);

        wallet.setBalance(wallet.getBalance() + request.getBalance());
        walletRepo.save(wallet);
    }


//    @Override
//    public void updateWalletBalance(Long walletId, double balance) {
//        Wallet wallet = walletRepo.findById(walletId)
//                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
//        wallet.setBalance(wallet.getBalance() + balance);
//        walletRepo.save(wallet);
//    }

//    /**
//     * Xử lý callback từ VNPay và cập nhật số dư ví.
//     */
//    @Override
//    public boolean processPaymentCallback(Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException {
//        if (isValidSignature(params)) {
//            // Lấy walletId từ vnp_TxnRef và số tiền
//            Long walletId = Long.parseLong(params.get("vnp_TxnRef"));
//            double amount = Double.parseDouble(params.get("vnp_Amount")) / 100;
//
//            // Cập nhật số dư ví
//            updateWalletBalance(walletId, amount);
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * Xác thực chữ ký từ VNPay.
//     */
//    private boolean isValidSignature(Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException {
//
//        String secretKey = "OIHP9HPL5O7QYVG4HIZ49LIT1LRG6GAU";
//
//        String vnpSecureHash = params.get("vnp_SecureHash");
//        String signData = params.entrySet().stream()
//                .filter(entry -> !entry.getKey().equals("vnp_SecureHash"))
//                .sorted(Map.Entry.comparingByKey())
//                .map(entry -> entry.getKey() + "=" + entry.getValue())
//                .collect(Collectors.joining("&"));
//
//        String generatedSignature = generateHMAC(secretKey, signData);
//        return vnpSecureHash.equalsIgnoreCase(generatedSignature);
//    }
//
//    /**
//     * Cập nhật số dư ví.
//     */
//    public void updateWalletBalance(Long walletId, double amount) {
//        Wallet wallet = walletRepo.findById(walletId)
//                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
//        wallet.setBalance(wallet.getBalance() + amount);
//        walletRepo.save(wallet);
//    }




}
