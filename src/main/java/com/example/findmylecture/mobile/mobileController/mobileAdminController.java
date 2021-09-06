package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mobile.mobiledto.MobileUserDto;
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

import java.util.List;

@RestController
@RequestMapping("/mobile/admin/")
public class mobileAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "view/lecturers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> viewLecturers() {
        List<UserDto> userDtoList = userService.findAllLecturers();
        return ResponseEntity.ok(userDtoList);
    }

    //post(//)
    //public returnObject name(@requestbody Dto dto){
    // }
}
