package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Account;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BreederDTO {

    Long breederID;
    String name;
    String address;
    String logo;
    Account account;
}
