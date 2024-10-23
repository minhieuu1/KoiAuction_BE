package com.bidkoi.auctionkoi.pojo;

import com.bidkoi.auctionkoi.enums.AuctionStatus;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID")
    Long roomId;

    @Column(name = "Winner")
    String winner;

    @OneToOne
    @JoinColumn(name = "KoiID", referencedColumnName = "KoiID")
    Koi koi;

    @Column(name = "AuctionID")
    Long auctionId;

//    LocalDateTime startTime;
//    LocalDateTime endTime;
//
//    @Enumerated(EnumType.STRING)
//    AuctionStatus status;


}
