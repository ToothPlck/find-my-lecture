package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.*;
import com.example.findmylecture.mobile.mobileService.MobileUserService;
import com.example.findmylecture.mobile.mobiledto.*;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.model.User;
import com.example.findmylecture.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mobile/admin/")
public class mobileAdminController {

    @Autowired
    private MobileUserService mobileUserService;
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

    @PostMapping(value = "add/lecturer", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> lecturerRegister(@RequestBody MobileUserDto mobileUserDto) throws Exception {
        userService.mobileSaveLecturer(mobileUserDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "view/lecturers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewLecturers() {
        List<UserDto> userDtoList = mobileUserService.findAllLecturers();
        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping(value = "add/module", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addModule(@RequestBody MobileModuleDto mobileModuleDto) throws Exception {
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setModuleId(mobileModuleDto.getModuleId());
        moduleDto.setModuleName(mobileModuleDto.getModuleName());
        moduleDto.setModuleDescription(mobileModuleDto.getModuleDescription());
        moduleService.save(moduleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "view/modules", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewModules() {
        List<ModuleDto> moduleDtoList = moduleService.getAllModules();
        List<MobileModuleDto> mobileModuleDtoList = new ArrayList<>();

        for (ModuleDto moduleDto : moduleDtoList) {
            MobileModuleDto mobileModuleDto = new MobileModuleDto();

            mobileModuleDto.setModuleId(moduleDto.getModuleId());
            mobileModuleDto.setModuleName(moduleDto.getModuleName());
            mobileModuleDto.setModuleDescription(moduleDto.getModuleDescription());

            if (moduleDto.getUser() != null) {
                MobileUserDto lecturer = new MobileUserDto();
                lecturer.setFirstname(moduleDto.getUser().getFirstname());
                lecturer.setLastname(moduleDto.getUser().getLastname());
                lecturer.setUsername(moduleDto.getUser().getUsername());

                mobileModuleDto.setMobileUserDto(lecturer);
            }

            mobileModuleDtoList.add(mobileModuleDto);
        }
        return ResponseEntity.ok(mobileModuleDtoList);
    }

    @PostMapping(value = "add/batch", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addBatch(@RequestBody MobileBatchDto mobileBatchDto) throws Exception {
        BatchDto batchDto = new BatchDto();

        batchDto.setBatchCode(mobileBatchDto.getBatchCode());
        batchDto.setBatchPeriod(mobileBatchDto.getBatchPeriod());
        batchDto.setIntakeDate(mobileBatchDto.getIntakeDate());

        batchService.saveBatch(batchDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "view/batches", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewBatches() {
        List<BatchDto> batchDtoList = batchService.findAllAssignableBatches();
        List<MobileBatchDto> mobileBatchDtoList = new ArrayList<>();

        for (BatchDto batchDto : batchDtoList) {
            MobileBatchDto mobileBatchDto = new MobileBatchDto();

            mobileBatchDto.setBatchId(batchDto.getBatchId());
            mobileBatchDto.setBatchCode(batchDto.getBatchCode());
            mobileBatchDto.setIntakeDate(batchDto.getIntakeDate());
            mobileBatchDto.setBatchPeriod(batchDto.getBatchPeriod());

            List<MobileModuleDto> mobileModuleDtoList = new ArrayList<>();
            if (batchDto.getModules() != null) {
                for (Module module : batchDto.getModules()) {
                    MobileModuleDto mobileModuleDto = new MobileModuleDto();

                    mobileModuleDto.setModuleId(module.getModuleId());
                    mobileModuleDto.setModuleName(module.getModuleName());

                    mobileModuleDtoList.add(mobileModuleDto);
                }
            }
            mobileBatchDto.setMobileModuleDto(mobileModuleDtoList);

            mobileBatchDtoList.add(mobileBatchDto);
        }
        return ResponseEntity.ok(mobileBatchDtoList);
    }

    @PostMapping(value = "add/room", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addRoom(@RequestBody MobileRoomDto mobileRoomDto) throws Exception {
        RoomDto roomDto = new RoomDto();

        roomDto.setRoomName(mobileRoomDto.getRoomName());
        roomDto.setRoomDescription(mobileRoomDto.getRoomDescription());
        roomDto.setRoomType(mobileRoomDto.getRoomType());

        roomService.save(roomDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "view/rooms", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewRooms() {
        List<Room> roomList = roomService.findAllRooms();
        List<MobileRoomDto> mobileRoomDtoList = new ArrayList<>();

        for (Room room : roomList) {
            MobileRoomDto mobileRoomDto = new MobileRoomDto();

            mobileRoomDto.setRoomId(room.getRoomId());
            mobileRoomDto.setRoomName(room.getRoomName());
            mobileRoomDto.setRoomType(room.getRoomType());
            mobileRoomDto.setRoomDescription(room.getRoomDescription());

            mobileRoomDtoList.add(mobileRoomDto);
        }
        return ResponseEntity.ok(mobileRoomDtoList);
    }
}
