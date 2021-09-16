package com.example.findmylecture;

import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.repository.TimeTableRepo;
import com.example.findmylecture.service.TimeTableService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TImeTableServiceTests {
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private TimeTableRepo timeTableRepo;

    @Test
    @Order(1)
    public void testScheduleOnAPreviousDate() {
        TimeTableDto timeTableDto = new TimeTableDto();
        timeTableDto.setDate("2021-01-01");
        timeTableDto.setStartTime("10:30:00");
        timeTableDto.setEndTme("12:00:00");

        boolean isTrue = false;

        try {
            timeTableService.save(timeTableDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("The selected date for the schedule is set in a previous date! Please select a future date for the schedule."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add schedule with past date [PASSED]");

    }

    @Test
    @Order(2)
    public void testScheduleOnInvalidTimes() {
        TimeTableDto timeTableDto = new TimeTableDto();
        timeTableDto.setDate("2021-12-01");
        timeTableDto.setStartTime("10:30:00");
        timeTableDto.setEndTme("09:00:00");

        boolean isTrue = false;

        try {
            timeTableService.save(timeTableDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("The schedule end time is set before the schedules start time! Please try again with correct time slots."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add schedule with end time set before start time [PASSED]");
    }

    @Test
    @Order(2)
    public void testScheduleOverFiveHours() {
        TimeTableDto timeTableDto = new TimeTableDto();
        timeTableDto.setDate("2021-12-01");
        timeTableDto.setStartTime("08:00:00");
        timeTableDto.setEndTme("14:00:00");

        boolean isTrue = false;

        try {
            timeTableService.save(timeTableDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("The maximum time period for a schedule is five (5) hours! Please try again with a time difference of under 5 hours."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add schedule of over five hours [PASSED]");
    }

    @Test
    @Order(4)
    public void testGetAllUpcomingSchedules() {
        List<TimeTableDto> timeTableDtoList = timeTableService.getTimeTable();

        boolean isTrue = timeTableDtoList.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all upcoming schedules [PASSED]");
    }
}
