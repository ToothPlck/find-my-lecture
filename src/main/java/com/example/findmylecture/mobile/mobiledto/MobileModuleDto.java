package com.example.findmylecture.mobile.mobiledto;

import java.util.List;

public class MobileModuleDto {
    private Long moduleId;
    private String moduleName;
    private String moduleDescription;
    private MobileUserDto mobileUserDto;
    List<MobileBatchDto> mobileBatchDto;
    List<MobileTimeTableDto> mobileTimeTableDto;

    public MobileModuleDto() {
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public MobileUserDto getMobileUserDto() {
        return mobileUserDto;
    }

    public void setMobileUserDto(MobileUserDto mobileUserDto) {
        this.mobileUserDto = mobileUserDto;
    }

    public List<MobileBatchDto> getMobileBatchDto() {
        return mobileBatchDto;
    }

    public void setMobileBatchDto(List<MobileBatchDto> mobileBatchDto) {
        this.mobileBatchDto = mobileBatchDto;
    }

    public List<MobileTimeTableDto> getMobileTimeTableDto() {
        return mobileTimeTableDto;
    }

    public void setMobileTimeTableDto(List<MobileTimeTableDto> mobileTimeTableDto) {
        this.mobileTimeTableDto = mobileTimeTableDto;
    }
}
