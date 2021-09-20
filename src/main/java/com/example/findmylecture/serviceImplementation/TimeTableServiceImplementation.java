package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.mailHandler.EmailService;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.TimeTable;
import com.example.findmylecture.model.User;
import com.example.findmylecture.repository.ModuleRepo;
import com.example.findmylecture.repository.TimeTableRepo;
import com.example.findmylecture.repository.UserRepo;
import com.example.findmylecture.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TimeTableServiceImplementation implements TimeTableService {

    @Autowired
    private TimeTableRepo timeTableRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private UserRepo userRepo;

    public String[] week() {
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK);
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    @Override
    public void save(TimeTableDto timeTableDto) throws Exception {
        TimeTable timeTable = new TimeTable();

        if (LocalTime.parse(timeTableDto.getStartTime()).isAfter(LocalTime.parse(timeTableDto.getEndTme()))) {
            throw new Exception("The schedule end time is set before the schedules start time! Please try again with correct time slots.");
        } else if (LocalDate.parse(timeTableDto.getDate()).isBefore(LocalDate.now())) {
            throw new Exception("The selected date for the schedule is set in a previous date! Please select a future date for the schedule.");
        } else {
            LocalTime start = LocalTime.parse(timeTableDto.getStartTime());
            LocalTime end = LocalTime.parse(timeTableDto.getEndTme());
            if (start.until(end, ChronoUnit.MINUTES) < 10) {
                throw new Exception("The minimum time period for a schedule is ten (10) minutes! Please try again with a time difference of over ten minutes.");
            } else if (start.until(end, ChronoUnit.HOURS) > 5) {
                throw new Exception("The maximum time period for a schedule is five (5) hours! Please try again with a time difference of under 5 hours.");
            }
        }

        timeTable.setTimeTableId(timeTableDto.getTimeTableId());
        timeTable.setDate(Date.valueOf(timeTableDto.getDate()));
        timeTable.setStartTime(LocalTime.parse(timeTableDto.getStartTime()));
        timeTable.setEndTime(LocalTime.parse(timeTableDto.getEndTme()));

        List<TimeTable> schedulesWithClassroom = timeTableRepo.findByClassroomAndDate(timeTableDto.getRooms().getRoomId(), Date.valueOf(timeTableDto.getDate()));
        for (TimeTable scheduleWithClassroom : schedulesWithClassroom) {
            if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(scheduleWithClassroom.getStartTime()))
                    &&
                    LocalTime.parse(timeTableDto.getStartTime()).isBefore(scheduleWithClassroom.getEndTime())) {
                throw new Exception("The selected class room is unavailable at the entered time slot!");
            } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(scheduleWithClassroom.getStartTime()))
                    &&
                    LocalTime.parse(timeTableDto.getEndTme()).isBefore(scheduleWithClassroom.getEndTime())) {
                throw new Exception("The selected class room is unavailable at the entered time slot!");
            } else if (LocalTime.parse(timeTableDto.getStartTime()) == scheduleWithClassroom.getStartTime()) {
                throw new Exception("The selected class room is unavailable at the entered time slot!");
            } else if (LocalTime.parse(timeTableDto.getEndTme()) == scheduleWithClassroom.getEndTime()) {
                throw new Exception("The selected class room is unavailable at the entered time slot!");
            } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(scheduleWithClassroom.getStartTime()))
                    &&
                    LocalTime.parse(timeTableDto.getEndTme()).isAfter(scheduleWithClassroom.getEndTime())) {
                throw new Exception("The selected class room is unavailable at the entered time slot!");
            }
        }

        List<TimeTable> schedulesOnDate = timeTableRepo.findBatchesByDate(Date.valueOf(timeTableDto.getDate()));

        for (Batch batch : timeTableDto.getBatches()) {
            if (!batch.getModules().contains(timeTableDto.getModules())) {
                throw new Exception("The following batch: " + batch.getBatchCode() +
                        "cannot have schedules for the following module: "
                        + timeTableDto.getModules().getModuleName() + "since the module is not assigned to the said batch");
            }
            for (TimeTable schedule : schedulesOnDate) {
                if (schedule.getBatches().contains(batch)) {
                    if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getEndTime())) {
                        throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isBefore(schedule.getEndTime())) {
                        throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getStartTime()) == schedule.getStartTime()) {
                        throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getEndTme()) == schedule.getEndTime()) {
                        throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getEndTime())) {
                        throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                    }
                }

            }
        }

        User lecturer = userRepo.findEmailOfLecturerByModuleId(timeTableDto.getModules().getModuleId());
        List<Module> lecturerModules = moduleRepo.findModulesByUsername(lecturer.getUsername());

        for (Module lecturerModule : lecturerModules) {
            for (TimeTable schedule : schedulesOnDate) {
                if (schedule.getModule() == lecturerModule) {
                    if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getEndTime())) {
                        throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isBefore(schedule.getEndTime())) {
                        throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getStartTime()) == schedule.getStartTime()) {
                        throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getEndTme()) == schedule.getStartTime()) {
                        throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getEndTime())) {
                        throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                    }
                }
            }
        }

        timeTable.setRoom(timeTableDto.getRooms());
        timeTable.setModule(timeTableDto.getModules());
        timeTable.setBatches(timeTableDto.getBatches());

        timeTableRepo.save(timeTable);
    }

    @Override
    public List<TimeTableDto> getTimeTable() {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();

        for (TimeTable timeTable : timeTableRepo.findAll()) {
            if (LocalDate.parse(timeTable.getDate().toString()).isAfter(LocalDate.now().minusDays(1))) {
                TimeTableDto timeTableDto = new TimeTableDto();

                timeTableDto.setTimeTableId(timeTable.getTimeTableId());
                timeTableDto.setModules(timeTable.getModule());
                timeTableDto.setDate(timeTable.getDate().toString());
                timeTableDto.setBatches(timeTable.getBatches());
                timeTableDto.setRooms(timeTable.getRoom());
                timeTableDto.setStartTime(timeTable.getStartTime().toString());
                timeTableDto.setEndTme(timeTable.getEndTime().toString());

                timeTableDtoList.add(timeTableDto);
            }
        }
        return timeTableDtoList;
    }

    @Override
    public TimeTableDto findTimetableByTimetableId(Long timetableId) {
        TimeTableDto timeTableDto = new TimeTableDto();
        TimeTable timeTable = timeTableRepo.getById(timetableId);

        timeTableDto.setTimeTableId(timetableId);
        timeTableDto.setModules(timeTable.getModule());
        timeTableDto.setBatches(timeTable.getBatches());
        timeTableDto.setRooms(timeTable.getRoom());
        timeTableDto.setDate(timeTable.getDate().toString());
        timeTableDto.setStartTime(timeTable.getStartTime().toString());
        timeTableDto.setEndTme(timeTable.getEndTime().toString());

        return timeTableDto;
    }

    @Override
    public void updateTimetable(Long timetableId, TimeTableDto timeTableDto) throws Exception {
        TimeTable timeTable = new TimeTable();

        Optional<TimeTable> optionalTimeTable = timeTableRepo.findById(timetableId);
        if (optionalTimeTable.isPresent()) {
            timeTable = optionalTimeTable.get();
        }

        if (timeTableDto != null) {
            if (LocalTime.parse(timeTableDto.getStartTime()).isAfter(LocalTime.parse(timeTableDto.getEndTme()))) {
                throw new Exception("The schedule end time is set before the schedules start time! Please try again with correct time slots.");
            } else if (LocalDate.parse(timeTableDto.getDate()).isBefore(LocalDate.now())) {
                throw new Exception("The selected date for the schedule is set in a previous date! Please select a future date for the schedule.");
            } else {
                LocalTime start = LocalTime.parse(timeTableDto.getStartTime());
                LocalTime end = LocalTime.parse(timeTableDto.getEndTme());
                if (start.until(end, ChronoUnit.MINUTES) < 10) {
                    throw new Exception("The minimum time period for a schedule is ten (10) minutes! Please try again with a time difference of over ten minutes.");
                } else if (start.until(end, ChronoUnit.HOURS) > 5) {
                    throw new Exception("The maximum time period for a schedule is five (5) hours! Please try again with a time difference of under 5 hours.");
                }
            }

            List<TimeTable> schedulesWithClassroom = timeTableRepo.findByClassroomAndDate(timeTableDto.getRooms().getRoomId(), Date.valueOf(timeTableDto.getDate()));
            for (TimeTable scheduleWithClassroom : schedulesWithClassroom) {
                if (scheduleWithClassroom != timeTable) {
                    if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(scheduleWithClassroom.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getStartTime()).isBefore(scheduleWithClassroom.getEndTime())) {
                        throw new Exception("The selected class room is unavailable at the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(scheduleWithClassroom.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isBefore(scheduleWithClassroom.getEndTime())) {
                        throw new Exception("The selected class room is unavailable at the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getStartTime()) == scheduleWithClassroom.getStartTime()) {
                        throw new Exception("The selected class room is unavailable at the entered time slot!");
                    } else if (LocalTime.parse(timeTableDto.getEndTme()) == scheduleWithClassroom.getEndTime()) {
                        throw new Exception("The selected class room is unavailable at the entered time slot!");
                    } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(scheduleWithClassroom.getStartTime()))
                            &&
                            LocalTime.parse(timeTableDto.getEndTme()).isAfter(scheduleWithClassroom.getEndTime())) {
                        throw new Exception("The selected class room is unavailable at the entered time slot!");
                    }
                }
            }

            List<TimeTable> schedulesOnDate = timeTableRepo.findBatchesByDate(Date.valueOf(timeTableDto.getDate()));

            for (Batch batch : timeTableDto.getBatches()) {
                if (!batch.getModules().contains(timeTable.getModule())) {
                    throw new Exception("The following batch: " + batch.getBatchCode() +
                            " cannot have schedules for the following module: "
                            + timeTable.getModule().getModuleName() + " since the module is not assigned to the said batch");
                }
                if (!timeTable.getBatches().contains(batch)) {
                    for (TimeTable schedule : schedulesOnDate) {
                        if (schedule != timeTable) {
                            if (schedule.getBatches().contains(batch)) {
                                if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(schedule.getStartTime()))
                                        &&
                                        LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getEndTime())) {
                                    throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                                } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getStartTime()))
                                        &&
                                        LocalTime.parse(timeTableDto.getEndTme()).isBefore(schedule.getEndTime())) {
                                    throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                                } else if (LocalTime.parse(timeTableDto.getStartTime()) == schedule.getStartTime()) {
                                    throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                                } else if (LocalTime.parse(timeTableDto.getEndTme()) == schedule.getEndTime()) {
                                    throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                                } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getStartTime()))
                                        &&
                                        LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getEndTime())) {
                                    throw new Exception("The selected batch/s has other schedules during the entered time slot!");
                                }
                            }
                        }

                    }
                }
            }

            User lecturer = userRepo.findEmailOfLecturerByModuleId(timeTable.getModule().getModuleId());
            List<Module> lecturerModules = moduleRepo.findModulesByUsername(lecturer.getUsername());

            for (Module lecturerModule : lecturerModules) {
                for (TimeTable schedule : schedulesOnDate) {
                    if (schedule != timeTable) {
                        if (schedule.getModule() == lecturerModule) {
                            if ((LocalTime.parse(timeTableDto.getStartTime()).isAfter(schedule.getStartTime()))
                                    &&
                                    LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getEndTime())) {
                                throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                            } else if ((LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getStartTime()))
                                    &&
                                    LocalTime.parse(timeTableDto.getEndTme()).isBefore(schedule.getEndTime())) {
                                throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                            } else if (LocalTime.parse(timeTableDto.getStartTime()) == schedule.getStartTime()) {
                                throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                            } else if (LocalTime.parse(timeTableDto.getEndTme()) == schedule.getStartTime()) {
                                throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                            } else if ((LocalTime.parse(timeTableDto.getStartTime()).isBefore(schedule.getStartTime()))
                                    &&
                                    LocalTime.parse(timeTableDto.getEndTme()).isAfter(schedule.getEndTime())) {
                                throw new Exception("The lecturer of the selected module is unavailable at the entered time slot!");
                            }
                        }
                    }
                }
            }

            timeTable.setRoom(timeTableDto.getRooms());
            timeTable.setDate(Date.valueOf(timeTableDto.getDate()));
            timeTable.setStartTime(LocalTime.parse(timeTableDto.getStartTime()));
            timeTable.setEndTime(LocalTime.parse(timeTableDto.getEndTme()));

            if (timeTableDto.getBatches().size() != 0) {
                timeTable.setBatches(timeTableDto.getBatches());
            }

            timeTableRepo.save(timeTable);

            //send emails
            List<Batch> batchList = timeTableDto.getBatches();
            if (optionalTimeTable.isPresent()) {
                List<Batch> previousBatches = optionalTimeTable.get().getBatches();
                if (batchList.size() == 0) {
                    for (Batch previousBatch : previousBatches) {
                        List<User> users = previousBatch.getUsers();
                        for (User user : users) {
                            emailService.updateTimeTableStudent(user.getEmail(), timeTable.getModule());
                        }
                    }
                } else {
                    for (Batch previousBatch : previousBatches) {
                        if (batchList.contains(previousBatch)) {
                            for (Batch batches : batchList) {
                                List<User> users = batches.getUsers();
                                for (User user : users) {
                                    emailService.updateTimeTableStudent(user.getEmail(), timeTable.getModule());
                                }
                            }
                        } else {
                            for (Batch batches : batchList) {
                                List<User> users = batches.getUsers();
                                for (User user : users) {
                                    emailService.updateTimeTableRemoveBatch(user.getEmail(), timeTable.getModule());
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void deleteTimeTable(Long timetableId) throws Exception {
        try {
            String moduleName = timeTableRepo.getModuleNameOfTimetable(timetableId);
            List<Batch> batchList = timeTableRepo.getBatchesOfTimeTable(timetableId);
            Module module = timeTableRepo.getModule(timetableId);

            timeTableRepo.deleteById(timetableId);

            //send email to all students in the batches
            if (batchList.size() != 0) {
                for (Batch batches : batchList) {
                    List<User> users = batches.getUsers();
                    for (User user : users) {
                        emailService.deleteTimetableStudent(user.getEmail(), moduleName);
                    }
                }
            }

            //send email to the lecturer of the module
            Long moduleId = module.getModuleId();
            User lecturer = userRepo.findEmailOfLecturerByModuleId(moduleId);
            emailService.deleteTimetableLecturer(lecturer.getEmail(), moduleName);
        } catch (Exception exception) {
            throw new Exception("An error occurred while deleting the schedule!");
        }
    }

    @Override
    public void updateWhereRoom(Long roomId) throws Exception {
        List<TimeTable> timeTables = timeTableRepo.findByRoomId(roomId);
        if (timeTables.size() != 0) {
            for (TimeTable timeTable : timeTables) {
                if ((LocalDate.parse(timeTable.getDate().toString()).isAfter(LocalDate.now()))
                        ||
                        LocalDate.parse(timeTable.getDate().toString()).isEqual(LocalDate.now())) {
                    throw new Exception("The classroom has upcoming schedules in the time table! Please update those schedules before removing the classroom.");
                }
            }
        }
    }

    @Override
    public void removeTimetable(Long batchId) throws Exception {
        List<TimeTable> schedules = timeTableRepo.findNumberOfBatchesOfTheSession(batchId);
        if (schedules.size() != 0) {
            for (TimeTable timeTable : schedules) {
                if ((LocalDate.parse(timeTable.getDate().toString()).isAfter(LocalDate.now()))
                        ||
                        LocalDate.parse(timeTable.getDate().toString()).isEqual(LocalDate.now())) {
                    throw new Exception("The batch has upcoming schedules in the time table! Please update those schedules before removing the batch.");
                }
            }
        }
    }

    @Override
    public List<TimeTableDto> searchTimetable(String keyword) {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();
        for (TimeTable timeTable : timeTableRepo.findByKeyword(keyword)) {

            if (LocalDate.parse(timeTable.getDate().toString()).isAfter(LocalDate.now().minusDays(1))) {
                TimeTableDto timeTableDto = new TimeTableDto();

                timeTableDto.setTimeTableId(timeTable.getTimeTableId());
                timeTableDto.setModules(timeTable.getModule());
                timeTableDto.setDate(timeTable.getDate().toString());
                timeTableDto.setBatches(timeTable.getBatches());
                timeTableDto.setRooms(timeTable.getRoom());
                timeTableDto.setStartTime(timeTable.getStartTime().toString());
                timeTableDto.setEndTme(timeTable.getEndTime().toString());

                timeTableDtoList.add(timeTableDto);
            }
        }
        return timeTableDtoList;
    }

    @Override
    public List<TimeTableDto> schedulesForToday() {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();

        for (TimeTable timeTable : timeTableRepo.schedulesForToday(Date.valueOf(LocalDate.now()))) {
            TimeTableDto timeTableDto = new TimeTableDto();

            timeTableDto.setTimeTableId(timeTable.getTimeTableId());
            timeTableDto.setModules(timeTable.getModule());
            timeTableDto.setDate(timeTable.getDate().toString());
            timeTableDto.setBatches(timeTable.getBatches());
            timeTableDto.setRooms(timeTable.getRoom());
            timeTableDto.setStartTime(timeTable.getStartTime().toString());
            timeTableDto.setEndTme(timeTable.getEndTime().toString());

            timeTableDtoList.add(timeTableDto);
        }
        return timeTableDtoList;
    }

    @Override
    public List<TimeTableDto> schedulesForThisWeek() {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();

        String[] thisWeek = week();
        for (String daysOfWeek : thisWeek) {
            if ((LocalDate.parse(daysOfWeek).isAfter(LocalDate.now())) || LocalDate.parse(daysOfWeek).equals(LocalDate.now())) {
                for (TimeTable timeTable : timeTableRepo.schedulesForToday(Date.valueOf(daysOfWeek))) {
                    TimeTableDto timeTableDto = new TimeTableDto();

                    timeTableDto.setTimeTableId(timeTable.getTimeTableId());
                    timeTableDto.setModules(timeTable.getModule());
                    timeTableDto.setDate(timeTable.getDate().toString());
                    timeTableDto.setBatches(timeTable.getBatches());
                    timeTableDto.setRooms(timeTable.getRoom());
                    timeTableDto.setStartTime(timeTable.getStartTime().toString());
                    timeTableDto.setEndTme(timeTable.getEndTime().toString());

                    timeTableDtoList.add(timeTableDto);
                }
            }
        }
        return timeTableDtoList;
    }

    @Override
    public List<TimeTableDto> lecturersSchedulesForThisWeek(String username) {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();
        List<Module> lecturerModules = moduleRepo.findModulesByUsername(username);
        String[] thisWeek = week();

        for (Module module : lecturerModules) {
            for (String daysOfWeek : thisWeek) {
                if ((LocalDate.parse(daysOfWeek).isAfter(LocalDate.now())) || LocalDate.parse(daysOfWeek).equals(LocalDate.now())) {
                    for (TimeTable timeTable : timeTableRepo.schedulesForModuleAndDate(module.getModuleId(), Date.valueOf(daysOfWeek))) {
                        TimeTableDto timeTableDto = new TimeTableDto();

                        timeTableDto.setTimeTableId(timeTable.getTimeTableId());
                        timeTableDto.setModules(timeTable.getModule());
                        timeTableDto.setDate(timeTable.getDate().toString());
                        timeTableDto.setBatches(timeTable.getBatches());
                        timeTableDto.setRooms(timeTable.getRoom());
                        timeTableDto.setStartTime(timeTable.getStartTime().toString());
                        timeTableDto.setEndTme(timeTable.getEndTime().toString());

                        timeTableDtoList.add(timeTableDto);
                    }
                }
            }
        }
        System.out.println(timeTableDtoList);
        return timeTableDtoList;
    }

    @Override
    public List<TimeTableDto> studentsSchedulesForThisWeek(String username) {
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();
        User student = userRepo.findUserByUsername(username);
        Batch studentsBatch = student.getBatch();
        String[] thisWeek = week();

        if (studentsBatch != null) {
            for (TimeTable timeTable : timeTableRepo.findSchedulesForBatch(studentsBatch.getBatchId())) {
                for (String daysOfWeek : thisWeek) {
                    if ((LocalDate.parse(daysOfWeek).isAfter(LocalDate.now())) || LocalDate.parse(daysOfWeek).equals(LocalDate.now())) {
                        if (timeTable.getDate().equals(Date.valueOf(daysOfWeek))) {
                            TimeTableDto timeTableDto = new TimeTableDto();

                            timeTableDto.setTimeTableId(timeTable.getTimeTableId());
                            timeTableDto.setModules(timeTable.getModule());
                            timeTableDto.setDate(timeTable.getDate().toString());
                            timeTableDto.setBatches(timeTable.getBatches());
                            timeTableDto.setRooms(timeTable.getRoom());
                            timeTableDto.setStartTime(timeTable.getStartTime().toString());
                            timeTableDto.setEndTme(timeTable.getEndTime().toString());

                            timeTableDtoList.add(timeTableDto);
                        }
                    }
                }
            }
        }
        return timeTableDtoList;
    }
}
