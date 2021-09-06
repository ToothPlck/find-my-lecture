package com.example.findmylecture.mobile.mobiledto;

import java.util.List;

public class MobileTimeTableDto {
    private Long timeTableId;
    private String Date;
    private String startTime;
    private String endTme;
    private MobileRoomDto mobileRoomDto;
    private MobileModuleDto mobileModuleDto;
    List<MobileBatchDto> mobileBatchDto;

    public MobileTimeTableDto() {
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(Long timeTableId) {
        this.timeTableId = timeTableId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTme() {
        return endTme;
    }

    public void setEndTme(String endTme) {
        this.endTme = endTme;
    }

    public MobileRoomDto getMobileRoomDto() {
        return mobileRoomDto;
    }

    public void setMobileRoomDto(MobileRoomDto mobileRoomDto) {
        this.mobileRoomDto = mobileRoomDto;
    }

    public MobileModuleDto getMobileModuleDto() {
        return mobileModuleDto;
    }

    public void setMobileModuleDto(MobileModuleDto mobileModuleDto) {
        this.mobileModuleDto = mobileModuleDto;
    }

    public List<MobileBatchDto> getMobileBatchDto() {
        return mobileBatchDto;
    }

    public void setMobileBatchDto(List<MobileBatchDto> mobileBatchDto) {
        this.mobileBatchDto = mobileBatchDto;
    }
}
