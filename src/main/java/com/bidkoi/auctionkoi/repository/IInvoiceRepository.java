package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;
import com.bidkoi.auctionkoi.pojo.Invoice;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.pojo.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

    //Invoice findByRoom_RoomId(Long roomId);

    Optional<Invoice> findByRoom_RoomId(Long roomId);

    Optional<Invoice> findByRoom_Koi_KoiId(Long koiId);

}
