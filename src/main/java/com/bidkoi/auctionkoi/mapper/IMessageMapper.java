package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.MessageDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Chat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IMessageMapper {
    List<MessageDTO> toMessagesDTO(List<Chat> chats);

}
