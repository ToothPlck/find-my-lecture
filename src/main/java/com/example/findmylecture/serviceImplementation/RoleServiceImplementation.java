package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.RoleDto;
import com.example.findmylecture.model.Role;
import com.example.findmylecture.repository.RoleRepo;
import com.example.findmylecture.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImplementation implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<RoleDto> getStaffRoles() {
        List<RoleDto> staffRoles = new ArrayList<>();

        for(Role role : roleRepo.findAll()){
            RoleDto roleDto = new RoleDto();
            if(!role.getRoleName().equals("STUDENT")){
                roleDto.setRoleId(role.getRoleId());
                roleDto.setRoleName(role.getRoleName());

                staffRoles.add(roleDto);
            }

        }
        return staffRoles;
    }
}
