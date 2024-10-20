package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StaffID")
    Long staffId;
    @Column(name = "First_name")
    String firstName;
    @Column(name = "Last_name")
    String lastName;
    String gender;


    @OneToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    Account account;
}
