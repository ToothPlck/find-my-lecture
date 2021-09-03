package com.example.findmylecture.repository;

import com.example.findmylecture.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findRoleByRoleName(String studentRole);
}
