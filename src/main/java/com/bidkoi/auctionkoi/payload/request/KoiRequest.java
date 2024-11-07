package com.bidkoi.auctionkoi.payload.request;

import com.bidkoi.auctionkoi.enums.KoiStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiRequest {

    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String video;
    String description;
    int rating;
    KoiStatus status;
    Double initialPrice;
    Double finalPrice = 0.0;
}
