package com.bidkoi.auctionkoi.payload.request;

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
    Double initialPrice;
    Double finalPrice;
}
