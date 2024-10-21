package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.enums.KoiStatus;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


import static org.mockito.Mockito.verify;
import  static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiServiceTest {
    @Autowired
    KoiService koiService;

    @MockBean
    IKoiRepository koiRepo;

    Long koiId;
    Koi koi;

    @BeforeEach
    void initData(){



        koi = new Koi();
    }


    //-----------------------------------APPROVE-----------------------------------

//    @Test
//    void approveKoi_success() {
//        //GIVEN: Koi existed with status Pending
//        koi.setKoiId(koiId);
//        koi.setStatus(String.valueOf(KoiStatus.PENDING));
//
//        //Exist koi
//        when(koiRepo.findById(koiId)).thenReturn(Optional.of(koi));
//
//        //WHEN
//        koiService.approveKoi(koiId);
//
//        //THEN: Verify the koi status is updated and saved
//        assertEquals(KoiStatus.ACCEPTED, koi.getStatus());
//        verify(koiRepo).save(koi);
//    }

    @Test
    void approveKoi_KoiNotFound() {
        //GIVEN: Koi not existed
        koi.setKoiId(koiId);

        //Not Exist koi
        when(koiRepo.findById(koiId)).thenReturn(Optional.empty());

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> koiService.approveKoi(koiId));

        //THEN: Expect an AppException with the correct error code and message
        assertEquals(404, exception.getErrorCode().getCode());
        assertEquals("Koi not found!", exception.getErrorCode().getMessage());
    }

    @Test
    void approveKoi_StatusNotPending() {
        //GIVEN: Koi existed with status Accept
        koi.setKoiId(koiId);
        koi.setStatus(KoiStatus.ACCEPTED);

        //Exist koi
        when(koiRepo.findById(koiId)).thenReturn(Optional.of(koi));

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> koiService.approveKoi(koiId));

        //THEN: Expect an AppException with the correct error code and message
        assertEquals(401, exception.getErrorCode().getCode());
        assertEquals("Koi status is not PENDING!", exception.getErrorCode().getMessage());
    }


    //------------------------------REJECT-------------------------------------------------
    @Test
    void rejectKoi_success() {
        //GIVEN: Koi existed with status Pending
        koi.setKoiId(koiId);
        koi.setStatus(KoiStatus.PENDING);

        //Exist koi
        when(koiRepo.findById(koiId)).thenReturn(Optional.of(koi));

        //WHEN
        koiService.rejectKoi(koiId);

        //THEN: Verify the koi status is updated and saved
        assertEquals(KoiStatus.REJECTED, koi.getStatus());
        verify(koiRepo).save(koi);
    }

    @Test
    void rejectKoi_KoiNotFound() {
        //GIVEN: Koi not existed
        koi.setKoiId(koiId);

        //Not Exist koi
        when(koiRepo.findById(koiId)).thenReturn(Optional.empty());

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> koiService.rejectKoi(koiId));

        //THEN: Expect an AppException with the correct error code and message
        assertEquals(404, exception.getErrorCode().getCode());
        assertEquals("Koi not found!", exception.getErrorCode().getMessage());
    }

    @Test
    void rejectKoi_StatusNotPending() {
        //GIVEN: Koi existed with status Reject
        koi.setKoiId(koiId);
        koi.setStatus(KoiStatus.REJECTED);

        //Exist koi
        when(koiRepo.findById(koiId)).thenReturn(Optional.of(koi));

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> koiService.rejectKoi(koiId));

        //THEN: Expect an AppException with the correct error code and message
        assertEquals(401, exception.getErrorCode().getCode());
        assertEquals("Koi status is not PENDING!", exception.getErrorCode().getMessage());
    }


}
