package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.WithdrawDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.enums.TransactionsEnum;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IWithdrawMapper;
import com.bidkoi.auctionkoi.payload.request.RejectWithdrawRequest;
import com.bidkoi.auctionkoi.payload.request.WithdrawRequest;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WithdrawService implements IWithdrawService {

    IAccountRepository accountRepo;
    IStaffRepository staffRepo;
    IWalletRepository walletRepo;
    ITransactionsRepository transactionRepo;
    IWithdrawRepository withdrawRepo;
    IWithdrawMapper withdrawMapper;

    @Override
    public WithdrawDTO createWithdraw(WithdrawRequest request, String accountId) {
        Account account = accountRepo.findById(accountId)
                    .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));

        Withdraw withdraw = Withdraw.builder()
                .amount(request.getAmount())
                .accountName(request.getAccountName())
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .status("PENDING")
                .withdrawDate(LocalDateTime.now())
                .account(account)
                .build();

        return withdrawMapper.toWithdrawDTO(withdrawRepo.save(withdraw));
    }

    @Override
    public WithdrawDTO approveWithdraw(Long withdrawId, Long staffId) {
        Withdraw withdraw = withdrawRepo.findById(withdrawId)
                .orElseThrow(()->new AppException(ErrorCode.WITHDRAW_NOT_FOUND));
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(()->new AppException(ErrorCode.STAFF_NOT_FOUND));

        Wallet wallet = walletRepo.findWalletByAccount(withdraw.getAccount());

        if(wallet.getBalance() < withdraw.getAmount()) {
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        if(withdraw.getStatus().equals("APPROVED")) {
            throw new AppException(ErrorCode.WITHDRAW_ALREADY_APPROVED);
        }

        wallet.setBalance(wallet.getBalance()-withdraw.getAmount());
        walletRepo.save(wallet);

        Transactions transactions = Transactions.builder()

                .amount(-withdraw.getAmount())
                .date(LocalDateTime.now())
                .description("withdraw money to account number: " + withdraw.getAccount().getId())
                .type(TransactionsEnum.WITHDRAW)
                .status("COMPLETED")
                .wallet(wallet)
                .build();

        transactionRepo.save(transactions);
        withdraw.setDescription("Withdraw approved successfully");
        withdraw.setStatus("APPROVED");
        withdraw.setStaff(staff);
        return withdrawMapper.toWithdrawDTO(withdrawRepo.save(withdraw));
    }

    @Override
    public WithdrawDTO rejectWithdraw(Long withdrawId, Long staffId, RejectWithdrawRequest request) {
        Withdraw withdraw = withdrawRepo.findById(withdrawId)
                .orElseThrow(()->new AppException(ErrorCode.WITHDRAW_NOT_FOUND));
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(()->new AppException(ErrorCode.STAFF_NOT_FOUND));

        if(withdraw.getStatus().equals("APPROVED")) {
            throw new AppException(ErrorCode.WITHDRAW_ALREADY_APPROVED);
        }

        withdraw.setStatus("REJECTED");
        withdraw.setDescription(request.getDescription());
        withdraw.setStaff(staff);

        Transactions transactions = Transactions.builder()
                .amount(withdraw.getAmount())
                .date(LocalDateTime.now())
                .description(request.getDescription())
                .type(TransactionsEnum.WITHDRAW)
                .status("FAIL")
                .build();
        transactionRepo.save(transactions);

        return withdrawMapper.toWithdrawDTO(withdrawRepo.save(withdraw));
    }

    @Override
    public List<Withdraw> getAllWithdraw() {
        return withdrawRepo.findAll();
    }

//    @Override
//    public WithdrawDTO getWithdrawById(Long withdrawId) {
//        Withdraw withdraw = withdrawRepo.findById(withdrawId)
//                .orElseThrow(()->new AppException(ErrorCode.WITHDRAW_NOT_FOUND));
//        return withdrawMapper.toWithdrawDTO(withdraw);
//    }

    @Override
    public WithdrawDTO getWithdrawByAccountId(String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        Withdraw withdraw = withdrawRepo.findWithdrawByAccount(account)
                .orElseThrow(()->new AppException(ErrorCode.WITHDRAW_NOT_FOUND));
        return withdrawMapper.toWithdrawDTO(withdraw);
    }


}
