package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WalletID")
    Long id;

    @Builder.Default
    Double balance = 0.0;

    @OneToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    Account account;
}
