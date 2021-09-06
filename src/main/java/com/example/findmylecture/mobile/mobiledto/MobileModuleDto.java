package com.example.findmylecture.mobile.mobiledto;

import java.util.List;

public class MobileModuleDto {
    private Long moduleId;
    private String moduleName;
    private String moduleDescription;
    private MobileUserDto mobileUserDto;
    List<MobileBatchDto> mobileBatchDto;
    List<MobileTimeTableDto> mobileTimeTableDto;

}
