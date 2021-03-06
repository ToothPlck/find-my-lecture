package com.example.findmylecture.service;

import com.example.findmylecture.dto.TimeTableDto;

import java.util.List;

public interface TimeTableService {

    void save(TimeTableDto timeTableDto) throws Exception;

    List<TimeTableDto> getTimeTable();

    TimeTableDto findTimetableByTimetableId(Long timetableId);

    void updateTimetable(Long timetableId, TimeTableDto timeTableDto) throws Exception;

    void deleteTimeTable(Long timetableId) throws Exception;

    void updateWhereRoom(Long roomId) throws Exception;

    void removeTimetable(Long batchId) throws Exception;

    List<TimeTableDto> searchTimetable(String keyword);

    List<TimeTableDto> schedulesForToday();

    List<TimeTableDto> schedulesForThisWeek();

    List<TimeTableDto> lecturersSchedulesForThisWeek(String username);

    List<TimeTableDto> studentsSchedulesForThisWeek(String username);
}
