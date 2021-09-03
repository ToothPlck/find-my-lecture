package com.example.findmylecture.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 50)
    private String roomName;
    @Column(length = 50)
    private String roomType;
    @Column(length = 200)
    private String roomDescription;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE})
    List<TimeTable> timeTables;

    public Room() {
    }

    public Room(Long roomId,
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
