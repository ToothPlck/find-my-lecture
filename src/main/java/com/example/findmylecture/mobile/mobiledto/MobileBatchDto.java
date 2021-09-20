package com.example.findmylecture.mobile.mobiledto;

import java.util.List;

public class MobileBatchDto {
    private Long batchId;
    private String batchCode;
    private String intakeDate;
    private Integer batchPeriod;
    List<MobileModuleDto> mobileModuleDto;
    List<MobileUserDto> mobileUserDto;
    List<MobileTimeTableDto> mobileTimeTableDto;

    public MobileBatchDto() {
    }

    public MobileBatchDto(Long batchId, String batchCode, String intakeDate, Integer batchPeriod, List<MobileModuleDto> mobileModuleDto, List<MobileUserDto> mobileUserDto, List<MobileTimeTableDto> mobileTimeTableDto) {
        this.batchId = batchId;
        this.batchCode = batchCode;
        this.intakeDate = intakeDate;
        this.batchPeriod = batchPeriod;
        this.mobileModuleDto = mobileModuleDto;
        this.mobileUserDto = mobileUserDto;
        this.mobileTimeTableDto = mobileTimeTableDto;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public Integer getBatchPeriod() {
        return batchPeriod;
    }

    public void setBatchPeriod(Integer batchPeriod) {
        this.batchPeriod = batchPeriod;
    }

    public List<MobileModuleDto> getMobileModuleDto() {
        return mobileModuleDto;
    }

    public void setMobileModuleDto(List<MobileModuleDto> mobileModuleDto) {
        this.mobileModuleDto = mobileModuleDto;
    }

    public List<MobileUserDto> getMobileUserDto() {
        return mobileUserDto;
    }

    public void setMobileUserDto(List<MobileUserDto> mobileUserDto) {
        this.mobileUserDto = mobileUserDto;
    }

    public List<MobileTimeTableDto> getMobileTimeTableDto() {
        return mobileTimeTableDto;
    }

    public void setMobileTimeTableDto(List<MobileTimeTableDto> mobileTimeTableDto) {
        this.mobileTimeTableDto = mobileTimeTableDto;
    }
}
