package com.example.findmylecture.controller;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/")
@PreAuthorize("hasAuthority('1')")
public class StudentController {

    @Autowired
    private UserService userService;
    @Autowired
    private TimeTableService timeTableService;

    //=========================================================================================================Home page
    @GetMapping("home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.studentsSchedulesForThisWeek(authentication.getName()));
        return "student_home";
    }

    //====================================================================================================view timetable
    @GetMapping("view/timetable")
    public String timetable(Model model, Authentication authentication){
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.getTimeTable());
        return "student_timetable";
    }

    //=================================================================================================search time table
    @GetMapping("search/timetable/{search}")
    public String searchTimeTable(@PathVariable(value = "search")String search, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.searchTimetable(search));
        return "student_timetable";
    }

    //======================================================================================================view account
    @GetMapping("view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "student_view_account";
    }

    //====================================================================================================update account
    @GetMapping("update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "student_update_account";
    }

    @PostMapping("update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Authentication authentication, Model model) {
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
