package com.example.findmylecture.service;

import com.example.findmylecture.dto.RoleDto;
import com.example.findmylecture.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleDto> getStaffRoles();
}
