package com.example.findmylecture.mobile.mobiledto;

import com.example.findmylecture.dto.TimeTableDto;

import java.util.List;

public class MobileRoomDto {
    private Long roomId;
    private String roomName;
    private String roomType;
    private String roomDescription;
    List<MobileTimeTableDto> mobileTimeTableDto;

}
