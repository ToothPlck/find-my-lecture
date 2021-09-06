package com.example.findmylecture.mobile.mobiledto;

import com.example.findmylecture.dto.UserDto;

import java.util.List;

public class MobileRoleDto {
    private Long roleId;
    private String roleName;
    List<MobileUserDto> mobileUserDto;

    public MobileRoleDto() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MobileUserDto> getMobileUserDto() {
        return mobileUserDto;
    }

    public void setMobileUserDto(List<MobileUserDto> mobileUserDto) {
        this.mobileUserDto = mobileUserDto;
    }
}
