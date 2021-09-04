package com.example.findmylecture.service;

import com.example.findmylecture.dto.UserDto;

import java.util.List;

public interface UserService {

    void save(UserDto userDto) throws Exception;

    UserDto findByUsername(String username);

    List<UserDto> findAllLecturers();

    List<UserDto> findAllStudents();

    void saveStudent(UserDto userDto) throws Exception;

    void updateStudent(UserDto userDto, String username) throws Exception;

    void updateLecturer(String username, UserDto userDto) throws Exception;

    UserDto findStudentsBatch(String username);

    void updateStudentBatch(String username, UserDto userDto) throws Exception;

    void deAssignStudentBatch(String username);

    void updateProfile(UserDto userDto, String username) throws Exception;

    void deleteUserByUsername(String username) throws Exception;

    void removeBatchFromStudents(Long batchId) throws Exception;

    UserDto updatable(String name);

    Long getUserRole(String name);
}
