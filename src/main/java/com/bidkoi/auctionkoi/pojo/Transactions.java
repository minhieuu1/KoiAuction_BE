package com.bidkoi.auctionkoi.pojo;

import com.bidkoi.auctionkoi.enums.TransactionsEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TransactionID")
    String transactionId;
    double amount;

    String description;

    @Enumerated(EnumType.STRING)
    TransactionsEnum type;

    String status;

    LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "WalletID",referencedColumnName = "WalletID")
    Wallet wallet;


}
