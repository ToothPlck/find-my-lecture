package com.example.findmylecture;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    private String addedUser;

    @Test
    @Order(1)
    public void testAddUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstname("firstname");
        userDto.setLastname("lastname");
        userDto.setEmail("testAddStudent@email.com");

        boolean saved = true;

        try {
            userService.saveStudent(userDto);
            addedUser = userService.findByEmail("testAddStudent@email.com");
        } catch (Exception exception) {
            saved = false;
        }

        assertTrue(saved);
        System.out.println("[TEST] Adding student [PASSED]");
    }

    @Test
    @Order(2)
    public void testAddUserWithExistingEmail() {
        UserDto userDto = new UserDto();
        userDto.setFirstname("firstname");
        userDto.setLastname("lastname");
        userDto.setEmail("testAddStudent@email.com");

        boolean isTrue = false;

        try {
            userService.saveStudent(userDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("A user with the entered email already exists in the system! Please try again with a different email."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add a user with an existing email [PASSED]");
    }

    @Test
    @Order(3)
    public void testGetAllStudents() {
        List<UserDto> users = userService.findAllStudents();

        boolean isTrue = users.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all students [PASSED]");
    }

    @Test
    @Order(4)
    public void testGetAllLecturers() {
        List<UserDto> users = userService.findAllLecturers();

        boolean isTrue = users.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all lecturers [PASSED]");
    }

    @Test
    @Order(5)
    public void testGetUserByUsername() {
        UserDto userDto = userService.findByUsername(addedUser);

        boolean isTrue = userDto != null;

        assertTrue(isTrue);
        System.out.println("[TEST] Get user by username [PASSED]");
    }

    @Test
    @Order(6)
    public void testDeleteUser() throws Exception {
        userService.deleteUserByUsername(addedUser);

        List<UserDto> users = userService.findAllStudents();

        boolean isTrue = true;

        for (UserDto userDto : users) {
            if (userDto.getUsername().equals(addedUser)) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);
        System.out.println("[TEST] Delete user [PASSED]");
    }
}
