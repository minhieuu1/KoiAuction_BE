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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffService implements IStaffService {

    IAccountRepository accountRepo;
    IStaffRepository staffRepo;
    IStaffMapper mapper;


    @Override
    public StaffDTO updateStaff(String accountId,StaffRequest request) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(accountRepo.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
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
