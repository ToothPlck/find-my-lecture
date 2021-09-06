package com.example.findmylecture.mobile.mobiledto;

import com.example.findmylecture.dto.TimeTableDto;

import java.util.List;

public class MobileRoomDto {
    private Long roomId;
    private String roomName;
    private String roomType;
    private String roomDescription;
    List<MobileTimeTableDto> mobileTimeTableDto;

    public MobileRoomDto() {
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

    public List<MobileTimeTableDto> getMobileTimeTableDto() {
        return mobileTimeTableDto;
    }

    public void setMobileTimeTableDto(List<MobileTimeTableDto> mobileTimeTableDto) {
        this.mobileTimeTableDto = mobileTimeTableDto;
    }
}
