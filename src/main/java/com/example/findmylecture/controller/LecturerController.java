package com.example.findmylecture.controller;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.service.*;
import com.example.findmylecture.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LecturerController {

    @Autowired
    private UserService userService;
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private BatchService batchService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private TimeTableValidator timeTableValidator;
    @Autowired
    private RoomValidator roomValidator;
    @Autowired
    private RoleValidator roleValidator;
    @Autowired
    private ModuleValidator moduleValidator;
    @Autowired
    private BatchValidator batchValidator;

    //=========================================================================================================Home page
    @GetMapping("/lecturer/home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.lecturersSchedulesForThisWeek(authentication.getName()));
        return "lecturer_home";
    }

    //======================================================================================================view account
    @GetMapping("/lecturer/view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        return "lecturer_view_account";
    }

    //====================================================================================================update account
    @GetMapping("/lecturer/update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        return "lecturer_update_account";
    }

    @PostMapping("/lecturer/update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Authentication authentication) throws Exception {
        userService.updateProfile(userDto, authentication.getName());
        return "redirect:/lecturer/view/account";
    }

}
