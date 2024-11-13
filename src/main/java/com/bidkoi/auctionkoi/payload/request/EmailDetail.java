package com.bidkoi.auctionkoi.payload.request;

import com.bidkoi.auctionkoi.pojo.Account;
import lombok.Data;

@Data
public class EmailDetail {
    Account receiver;
    String subject;
    String link;

}
