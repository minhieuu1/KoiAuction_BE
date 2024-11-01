package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.MessageDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IMessageMapper;
import com.bidkoi.auctionkoi.pojo.Chat;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.repository.IChatRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService implements IMessageService {

    IChatRepository chatRepo;
    IRoomRepository roomRepo;
    IMessageMapper mapper;

    @Override
    public MessageDTO save(MessageDTO request, Long roomId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Chat chat = Chat.builder()
                .senderName(request.getSenderName())
                .message(request.getMessage())
                .date(LocalDateTime.now())
                .room(room)
                .build();



        chatRepo.save(chat);

        return MessageDTO.builder()
                .senderName(request.getSenderName())
                .message(request.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

    @Override
    public List<MessageDTO> getMessage(Long roomId) {
        List<Chat> chats = chatRepo.findByRoom_RoomId(roomId);
        List<MessageDTO> dtos = mapper.toMessagesDTO(chats);
        return dtos;
    }
}
