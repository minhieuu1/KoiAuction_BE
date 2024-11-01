package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.MessageDTO;

import java.util.List;

public interface IMessageService {
    MessageDTO save(MessageDTO message,Long roomId);
    List<MessageDTO> getMessage(Long roomId);
}
