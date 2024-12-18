package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Staff;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiDTO {
    Long koiId;
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
    Double immediatePrice;
    Breeder breeder;
    Staff staff;
}
