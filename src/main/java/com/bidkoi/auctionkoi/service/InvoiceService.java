package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IInvoiceMapper;
import com.bidkoi.auctionkoi.pojo.Invoice;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.pojo.Shipping;
import com.bidkoi.auctionkoi.repository.IInvoiceRepository;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import com.bidkoi.auctionkoi.repository.IShippingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService implements IInvoiceService {

    IKoiRepository koiRepo;
    IRoomRepository roomRepo;
    IShippingRepository shippingRepo;
    IInvoiceRepository invoiceRepo;
    IInvoiceMapper invoiceMapper;

//    @Override
//    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
//
//        Room room = roomRepo.findById(invoiceDTO.getRoomId())
//                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
//
//        Shipping shipping = shippingRepo.findById(invoiceDTO.getShippingId())
//                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));
//
//        Invoice invoice = Invoice.builder()
//                .date(LocalDateTime.now())
//                .room(room)
//                .shipping(shipping)
//                .build();
//
//        return invoiceMapper.toInvoiceDTO(invoiceRepo.save(invoice));
//    }

    @Override
    public InvoiceDTO createInvoice(Long roomId) {

        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi(); //lấy koi từ room

        Shipping shipping = shippingRepo.findByKoi(koi);
        if(shipping == null) {
            throw new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND);
        }


        Invoice invoice = Invoice.builder()
                .date(LocalDateTime.now())
                .room(room)
                .shipping(shipping)
                .build();

        return invoiceMapper.toInvoiceDTO(invoiceRepo.save(invoice));
    }

    @Override
    public InvoiceDTO getInvoice(Long roomId) {

        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Invoice invoice = invoiceRepo.findByRoom_RoomId(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        return invoiceMapper.toInvoiceDTO(invoice);
    }


    @Override
    public InvoiceDTO getInvoiceByKoi(Long koiId) {

        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(() -> new AppException(ErrorCode.KOI_NOT_FOUND));

        Invoice invoice = invoiceRepo.findByRoom_Koi_KoiId(koiId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceRepo.findAll();
    }
}
