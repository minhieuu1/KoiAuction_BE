package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Chat;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom_RoomId(Long roomId);
    Chat findByRoomAndSenderName(Room room,String name);

}
