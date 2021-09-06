package com.example.findmylecture.mobile.mobiledto;

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

    public MobileUserDto() {
    }

    public MobileUserDto(String username, String firstname, String lastname, String email, String nic, String contactNumber, String password, MobileRoleDto mobileRoleDto, MobileBatchDto mobileBatchDto, List<MobileModuleDto> mobileModuleDto) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nic = nic;
        this.contactNumber = contactNumber;
        this.password = password;
        this.mobileRoleDto = mobileRoleDto;
        this.mobileBatchDto = mobileBatchDto;
        this.mobileModuleDto = mobileModuleDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MobileRoleDto getMobileRoleDto() {
        return mobileRoleDto;
    }

    public void setMobileRoleDto(MobileRoleDto mobileRoleDto) {
        this.mobileRoleDto = mobileRoleDto;
    }

    public MobileBatchDto getMobileBatchDto() {
        return mobileBatchDto;
    }

    public void setMobileBatchDto(MobileBatchDto mobileBatchDto) {
        this.mobileBatchDto = mobileBatchDto;
    }

    public List<MobileModuleDto> getMobileModuleDto() {
        return mobileModuleDto;
    }

    public void setMobileModuleDto(List<MobileModuleDto> mobileModuleDto) {
        this.mobileModuleDto = mobileModuleDto;
    }
}
