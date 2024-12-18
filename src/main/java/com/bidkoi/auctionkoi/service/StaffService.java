package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IStaffMapper;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Staff;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.bidkoi.auctionkoi.repository.IStaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffService implements IStaffService {

    IAccountRepository accountRepo;
    IStaffRepository staffRepo;
    IStaffMapper mapper;


    @Override
    public StaffDTO getStaff(String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return mapper.toStaffDTO(staffRepo.findByAccount(account));
    }

    @Override
    public StaffDTO updateStaff(String accountId,StaffRequest request) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(accountRepo.existsByEmailAndIdIsNot(request.getEmail(),accountId)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }else if(accountRepo.existsByPhoneAndIdIsNot(request.getPhone(),accountId)){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        accountRepo.save(account);

        Staff staff = staffRepo.findByAccount(account);
        staff.setAccount(account);
        mapper.updateStaff(staff,request);
        staffRepo.save(staff);


        return mapper.toStaffDTO(staff);
    }


}
