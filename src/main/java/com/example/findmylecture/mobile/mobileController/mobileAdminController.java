package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.*;
import com.example.findmylecture.mobile.mobileService.MobileUserService;
import com.example.findmylecture.mobile.mobiledto.*;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.service.TimeTableService;
import com.example.findmylecture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mobile/admin/")
public class mobileAdminController {

    @Autowired
    private MobileUserService mobileUserService;
    @Autowired
    private TimeTableService timeTableService;

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


    @GetMapping(value = "view/lecturers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewLecturers() {
        List<UserDto> userDtoList = mobileUserService.findAllLecturers();
        return ResponseEntity.ok(userDtoList);
    }

    //post(//)
    //public returnObject name(@requestbody Dto dto){
    // }
}
