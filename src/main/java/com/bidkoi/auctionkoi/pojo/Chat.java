package com.bidkoi.auctionkoi.pojo;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long no;
    @Column(name = "name")
    String senderName;
    String message;
    LocalDateTime date;



    @ManyToOne
    @JoinColumn(name = "RoomID",referencedColumnName = "RoomID")
    Room room;

}
