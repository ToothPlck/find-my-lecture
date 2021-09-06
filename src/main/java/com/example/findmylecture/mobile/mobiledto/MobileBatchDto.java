package com.example.findmylecture.mobile.mobiledto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public class MobileBatchDto {
    private Long batchId;
    private String batchCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String intakeDate;
    private Integer batchPeriod;
    List<MobileModuleDto> mobileModuleDto;
    List<MobileUserDto> mobileUserDto;
    List<MobileTimeTableDto> mobileTimeTableDto;
}
