package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.mobile.mobiledto.MobileBatchDto;
import com.example.findmylecture.mobile.mobiledto.MobileModuleDto;
import com.example.findmylecture.mobile.mobiledto.MobileRoomDto;
import com.example.findmylecture.mobile.mobiledto.MobileTimeTableDto;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Room;
import com.example.findmylecture.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mobile/student")
public class mobileStudentController {

    @Autowired
    private TimeTableService timeTableService;

    @GetMapping(value = "/timetable/today", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewSchedulesForToday(Authentication authentication) {

        List<TimeTableDto> timeTableDtoList = timeTableService.studentsSchedulesForThisWeek(authentication.getName());
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
}
