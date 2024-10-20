package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

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
    Long walletId;


    @Builder.Default
    Double balance = 0.0;

    @OneToOne
    @JoinColumn(name =  "AccountID", referencedColumnName = "AccountID")
    Account account;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL) //khi mà lưu Wallet thì lưu luôn Transactions
    List<Transactions> transactions;

}
