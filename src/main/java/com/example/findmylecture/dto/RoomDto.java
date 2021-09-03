package com.example.findmylecture.dto;

import com.example.findmylecture.model.TimeTable;

import java.util.List;

public class RoomDto {
    private Long roomId;
    private String roomName;
    private String roomType;
    private String roomDescription;

    List<TimeTable> timeTables;

    public RoomDto() {
    }

    public RoomDto(Long roomId,
                   String roomName,
                   String roomType,
                   String roomDescription,
                   List<TimeTable> timeTables) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomDescription = roomDescription;
        this.timeTables = timeTables;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public List<TimeTable> getTimeTable() {
        return timeTables;
    }

    public void setTimeTable(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
