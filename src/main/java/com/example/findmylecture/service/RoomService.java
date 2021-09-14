package com.example.findmylecture.service;

import com.example.findmylecture.dto.RoomDto;
import com.example.findmylecture.model.Room;

import java.util.List;

public interface RoomService {

    void save(RoomDto roomDto) throws Exception;

    Room findByRoomName(String roomName);

    List<Room> findAllRooms();

    Room findByRoomId(Long roomId);

    Room updateRoom(Long roomId, RoomDto roomDto) throws Exception;

    void deleteRoomByRoomId(Long roomId);

    List<RoomDto> getRoomsForTimeTable();

    RoomDto saveClassroom(RoomDto roomDto) throws Exception;
}
