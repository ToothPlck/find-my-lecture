package com.example.findmylecture.mobile.mobiledto;

import com.example.findmylecture.dto.BatchDto;
import com.example.findmylecture.dto.ModuleDto;
import com.example.findmylecture.dto.RoleDto;

import java.util.List;

public class MobileUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String nic;
    private String contactNumber;
    private String password;
    private MobileRoleDto mobileRoleDto;
    private MobileBatchDto mobileBatchDto;
    List<MobileModuleDto> mobileModuleDto;

}
