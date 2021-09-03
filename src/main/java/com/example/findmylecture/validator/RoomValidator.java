package com.example.findmylecture.validator;

import com.example.findmylecture.dto.RoomDto;
import com.example.findmylecture.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoomValidator implements Validator {

    @Autowired
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RoomDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomDto roomDto = (RoomDto) target;

        //roomName
        ValidationUtils.rejectIfEmpty(errors, "roomName", "NotEmpty");

        if(roomDto.getRoomName().length() < 2 || roomDto.getRoomName().length() > 20){
            errors.rejectValue("roomName", "Size.roomForm.roomName");
        }
        if(roomService.findByRoomName(roomDto.getRoomName()) != null){
            errors.rejectValue("roomName", "Duplicate.roomForm.roomName");
        }

        //roomType
        ValidationUtils.rejectIfEmpty(errors, "roomType", "NotEmpty");

        if(roomDto.getRoomName().length() < 3 || roomDto.getRoomName().length() > 20){
            errors.rejectValue("type", "Size.roomForm.type");
        }

        //description
        if(roomDto.getRoomDescription().length() < 3 || roomDto.getRoomDescription().length() > 250){
            errors.rejectValue("roomDescription", "Size.roomForm.description");
        }
    }
}
