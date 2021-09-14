package com.example.findmylecture;

import com.example.findmylecture.dto.RoomDto;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class RoomServiceTests {

    @Autowired
    private RoomService roomService;

    Long addedRoomId;

    @Test
    public void testAddClassroom() throws Exception {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomName("testRoomName");
        roomDto.setRoomType("type");

        RoomDto result = roomService.saveClassroom(roomDto);
        addedRoomId = result.getRoomId();

        assertNotNull(result);
        System.out.println("[TEST] Adding new classroom [PASSED]");
    }

    @Test
    public void testAddClassroomWithExistingClassroomName() {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomName("testRoomName");
        roomDto.setRoomType("type");

        boolean isTrue = false;

        try {
            roomService.saveClassroom(roomDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("Another classroom with the entered classroom name exists in the system! Please try again with a different classroom name."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add a classroom with an existing classroom name [PASSED]");
    }

    @Test
    public void getAllClassrooms() {
        List<RoomDto> roomDtoList = roomService.getRoomsForTimeTable();

        boolean isTrue = roomDtoList.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all classrooms [PASSED]");
    }

    @Test
    public void getClassroomById() {
        Room room = roomService.findByRoomId(addedRoomId);

        boolean isTrue = room != null;

        assertTrue(isTrue);
        System.out.println("[TEST] Get classroom by id [PASSED]");
    }

    @Test
    public void deleteClassroom() {
        roomService.deleteRoomByRoomId(addedRoomId);

        List<RoomDto> roomDtoList = roomService.getRoomsForTimeTable();

        boolean isTrue = true;

        for (RoomDto roomDto : roomDtoList) {
            if (roomDto.getRoomId().equals(addedRoomId)) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);
        System.out.println("[TEST] Delete classroom [PASSED]");
    }
}
