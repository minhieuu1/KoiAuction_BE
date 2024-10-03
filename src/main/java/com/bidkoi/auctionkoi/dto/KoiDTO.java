package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Breeder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiDTO {
    String koiId;
    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String video;
    String description;
    int method;
    Double initialPrice;
    Double finalPrice;
    Breeder breeder;
}
