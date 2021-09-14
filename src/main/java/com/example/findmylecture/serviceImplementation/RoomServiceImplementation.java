package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.RoomDto;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.repository.RoomRepo;
import com.example.findmylecture.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImplementation implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public void save(RoomDto roomDto) throws Exception {
        List<Room> roomsWithRoomName = roomRepo.findByRoomName(roomDto.getRoomName());
        if (roomsWithRoomName.size() != 0) {
            throw new Exception("Another classroom with the entered classroom name exists in the system! Please try again with a different classroom name.");
        }
        Room room = new Room();

        room.setRoomId(roomDto.getRoomId());
        room.setRoomName(roomDto.getRoomName());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomDescription(roomDto.getRoomDescription());

        roomRepo.save(room);
    }

    @Override
    public Room findByRoomName(String roomName) {
        return roomRepo.findRoomByRoomName(roomName);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Room findByRoomId(Long roomId) {
        return roomRepo.findRoomByRoomId(roomId);
    }

    @Override
    public Room updateRoom(Long roomId, RoomDto roomDto) throws Exception {
        Room room = new Room();

        Optional<Room> thisRoom = Optional.ofNullable(roomRepo.findRoomByRoomId(roomId));

        if (thisRoom.isPresent()) {
            room = thisRoom.get();
        }

        List<Room> roomsWithRoomName = roomRepo.findByRoomName(roomDto.getRoomName());
        if (roomsWithRoomName.size() != 0) {
            for (Room roomWithRoomName : roomsWithRoomName) {
                if (!roomId.equals(roomWithRoomName.getRoomId())) {
                    throw new Exception("Another classroom with the entered classroom name exists in the system! Please try again with a different classroom name.");
                }
            }
        }

        room.setRoomName(roomDto.getRoomName());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomDescription(roomDto.getRoomDescription());

        return roomRepo.save(room);
    }

    @Override
    public RoomDto saveClassroom(RoomDto roomDto) throws Exception{
        List<Room> roomsWithRoomName = roomRepo.findByRoomName(roomDto.getRoomName());
        if (roomsWithRoomName.size() != 0) {
            throw new Exception("Another classroom with the entered classroom name exists in the system! Please try again with a different classroom name.");
        }
        Room room = new Room();

        room.setRoomId(roomDto.getRoomId());
        room.setRoomName(roomDto.getRoomName());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomDescription(roomDto.getRoomDescription());

        roomRepo.save(room);

        roomDto.setRoomId(room.getRoomId());
        return roomDto;
    }

    @Override
    public void deleteRoomByRoomId(Long roomId) {
        roomRepo.deleteById(roomId);
    }

    @Override
    public List<RoomDto> getRoomsForTimeTable() {
        List<RoomDto> rooms = new ArrayList<>();

        for (Room room : roomRepo.findAll()) {
            RoomDto roomDto = new RoomDto();

            roomDto.setRoomId(room.getRoomId());
            roomDto.setRoomType(room.getRoomType());
            roomDto.setRoomName(room.getRoomName());

            rooms.add(roomDto);
        }
        return rooms;
    }
}
