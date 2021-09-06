package com.example.findmylecture.mobile.mobiledto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public class MobileTimeTableDto {
    private Long timeTableId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String Date;
    private String startTime;
    private String endTme;
    private MobileRoomDto mobileRoomDto;
    private MobileModuleDto mobileModuleDto;
    List<MobileBatchDto> mobileBatchDto;
}
