package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WithdrawID")
    Long withdrawId;

    @Column(name = "Amount")
    Double amount;

    @Column(name = "Account_Name")
    String accountName;

    @Column(name = "Account_Number")
    String accountNumber;

    @Column(name = "Bank_Name")
    String bankName;

    @Column(name = "Withdraw_Date")
    LocalDateTime withdrawDate;

    @Column(name = "Description")
    String description;

    @Column(name = "Status")
    String status;

    @ManyToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    Account account;

    @ManyToOne
    @JoinColumn(name = "StaffID", referencedColumnName = "StaffID")
    Staff staff;
}
