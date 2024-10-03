package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Koi;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDTO {
    Long no;
    Koi koi;
}
