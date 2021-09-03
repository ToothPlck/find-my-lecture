package com.example.findmylecture.repository;

import com.example.findmylecture.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

    Room findRoomByRoomName(String roomName);

    Room findRoomByRoomId(Long roomId);

    @Query("from Room r where r.roomName=:roomName")
    List<Room> findByRoomName(String roomName);
}
