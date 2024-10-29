package com.bidkoi.auctionkoi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceID")
    Long invoiceId;

    @Column(name = "Date")
    LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
    Room room;

    @OneToOne
    @JoinColumn(name = "ShippingID", referencedColumnName = "ShippingID")
    Shipping shipping;
}
