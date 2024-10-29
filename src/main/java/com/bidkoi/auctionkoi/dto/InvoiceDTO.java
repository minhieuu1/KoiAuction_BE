package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.pojo.Shipping;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDTO {
    Long invoiceId;
    LocalDateTime date;
    Room room;
    Shipping shipping;
}
