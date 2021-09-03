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
public class StudentController {

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
    @GetMapping("/student/home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.studentsSchedulesForThisWeek(authentication.getName()));
        return "student_home";
    }

    //======================================================================================================view account
    @GetMapping("/student/view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "student_view_account";
    }

    //====================================================================================================update account
    @GetMapping("/student/update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "student_update_account";
    }

    @PostMapping("/student/update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Authentication authentication, Model model) throws Exception {
        try {
            userService.updateProfile(userDto, authentication.getName());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Account updated");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "student_update_account";
        }
        return "student_view_account";
    }

}
