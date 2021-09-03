package com.example.findmylecture.dto;

import com.example.findmylecture.model.User;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {
    private Long roleId;
    private String roleName;
    List<User> users;

    public RoleDto() {
    }

    public RoleDto(Long roleId,
                   String roleName,
                   List<User> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
