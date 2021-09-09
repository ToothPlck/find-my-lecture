package com.example.findmylecture.mobile.mobileService;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.User;
import com.example.findmylecture.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MobileUserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MobileModuleService mobileModuleService;

    public List<UserDto> findAllLecturers() {
        List<UserDto> lecturerList = new ArrayList<>();

        for (User user : userRepo.findAllLecturers()) {
            UserDto userDto = new UserDto();

            userDto.setUsername(user.getUsername());
            userDto.setFirstname(user.getFirstname());
            userDto.setLastname(user.getLastname());
            userDto.setContactNumber(user.getContactNumber());
            userDto.setEmail(user.getEmail());
            userDto.setNic(user.getNic());

            lecturerList.add(userDto);
        }
        return lecturerList;
    }
}
