package com.example.findmylecture.dto;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Role;
import java.util.List;

public class UserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String nic;
    private String contactNumber;
    private String password;
    private Role role;
    private Batch batch;
    List<Module> modules;

    public UserDto() {
    }

    public UserDto(String username,
                   String firstname,
                   String lastname,
                   String email,
                   String nic,
                   String contactNumber,
                   String password,
                   Role role,
                   Batch batch,
                   List<Module> modules) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nic = nic;
        this.contactNumber = contactNumber;
        this.password = password;
        this.role = role;
        this.batch = batch;
        this.modules = modules;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
