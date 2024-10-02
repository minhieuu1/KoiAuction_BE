package com.bidkoi.auctionkoi.payload.request;

import com.bidkoi.auctionkoi.pojo.Breeder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiCreationRequest {

    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String description;
    Double initialPrice;
    Double finalPrice;
}
