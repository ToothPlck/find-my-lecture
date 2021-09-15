package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.BatchDto;
import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mobile.mobiledto.*;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mobile/handler")
public class mobileHandlerController {

    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/timetable/today", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewSchedulesForToday() {
        List<TimeTableDto> timeTableDtoList = timeTableService.schedulesForToday();
        List<MobileBatchDto> mobileBatchDtoList = new ArrayList<>();

        List<MobileTimeTableDto> mobileTimeTableDtoList = new ArrayList<>();

        for (TimeTableDto timeTableDto : timeTableDtoList) {

            MobileTimeTableDto mobileTimeTableDto = new MobileTimeTableDto();

            MobileRoomDto mobileRoomDto = new MobileRoomDto();
            Room room = timeTableDto.getRooms();

            mobileRoomDto.setRoomId(room.getRoomId());
            mobileRoomDto.setRoomType(room.getRoomType());
            mobileRoomDto.setRoomName(room.getRoomName());

            MobileModuleDto mobileModuleDto = new MobileModuleDto();
            Module module = timeTableDto.getModules();

            mobileModuleDto.setModuleId(module.getModuleId());
            mobileModuleDto.setModuleName(module.getModuleName());

            mobileTimeTableDto.setTimeTableId(timeTableDto.getTimeTableId());
            mobileTimeTableDto.setDate(timeTableDto.getDate());
            mobileTimeTableDto.setStartTime(timeTableDto.getStartTime());
            mobileTimeTableDto.setEndTme(timeTableDto.getEndTme());
            mobileTimeTableDto.setMobileModuleDto(mobileModuleDto);
            mobileTimeTableDto.setMobileRoomDto(mobileRoomDto);

            for (Batch batch : timeTableDto.getBatches()) {
                MobileBatchDto mobileBatchDto = new MobileBatchDto();

                mobileBatchDto.setBatchId(batch.getBatchId());
                mobileBatchDto.setBatchCode(batch.getBatchCode());

                mobileBatchDtoList.add(mobileBatchDto);
            }

            mobileTimeTableDto.setMobileBatchDto(mobileBatchDtoList);

            mobileTimeTableDtoList.add(mobileTimeTableDto);
        }
        return ResponseEntity.ok(mobileTimeTableDtoList);
    }

    @PostMapping(value = "add/student", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> studentRegister(@RequestBody MobileUserDto mobileUserDto) throws Exception {
        UserDto userDto = new UserDto();

        userDto.setFirstname(mobileUserDto.getFirstname());
        userDto.setLastname(mobileUserDto.getLastname());
        userDto.setEmail(mobileUserDto.getEmail());
        userDto.setNic(mobileUserDto.getNic());
        userDto.setContactNumber(mobileUserDto.getContactNumber());

        userService.saveStudent(userDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "view/students", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewStudents() {
        List<UserDto> userDtoList = userService.findAllStudents();
        List<MobileUserDto> mobileUserDtoList = new ArrayList<>();

        for (UserDto userDto : userDtoList) {
            MobileUserDto mobileUserDto = new MobileUserDto();

            mobileUserDto.setUsername(userDto.getUsername());
            mobileUserDto.setFirstname(userDto.getFirstname());
            mobileUserDto.setLastname(userDto.getLastname());
            mobileUserDto.setEmail(userDto.getEmail());
            mobileUserDto.setContactNumber(userDto.getContactNumber());

            mobileUserDtoList.add(mobileUserDto);
        }

        return ResponseEntity.ok(mobileUserDtoList);
    }

    @PostMapping(value = "add/timetable", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addTimetable(@RequestBody MobileTimeTableDto mobileTimeTableDto) throws Exception {
        TimeTableDto timeTableDto = new TimeTableDto();
        List<Batch> batchList = new ArrayList<>();

        timeTableDto.setDate(mobileTimeTableDto.getDate());
        timeTableDto.setStartTime(mobileTimeTableDto.getStartTime());
        timeTableDto.setEndTme(mobileTimeTableDto.getEndTme());
        timeTableDto.setRooms(roomService.findByRoomId(mobileTimeTableDto.getMobileRoomDto().getRoomId()));
        timeTableDto.setModules(moduleService.findById(mobileTimeTableDto.getMobileModuleDto().getModuleId()));

        for (MobileBatchDto mobileBatchDto : mobileTimeTableDto.getMobileBatchDto()) {
            Batch batch = new Batch();
            BatchDto batchDto = batchService.findBatchByBatchId(mobileBatchDto.getBatchId());

            batch.setBatchId(batchDto.getBatchId());
            batch.setBatchCode(batchDto.getBatchCode());

            batchList.add(batch);
        }

        timeTableDto.setBatches(batchList);

        timeTableService.save(timeTableDto);

        return ResponseEntity.ok().build();
    }
}
