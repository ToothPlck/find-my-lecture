package com.example.findmylecture.controller;

import com.example.findmylecture.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @Autowired
    private UserService userService;
    @Autowired
    private TimeTableService timeTableService;

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute("error", "The username and password did not match! Please try again.");
        }
        return "login";
    }

    @GetMapping("/home")
    public String successLogin(Model model, Authentication authentication) {
        Long userDto = userService.getUserRole(authentication.getName());

        if (userDto == Long.parseLong("3")) {
            return "redirect:/admin/home";
        }
        if (userDto == Long.parseLong("4")) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("schedules", timeTableService.schedulesForToday());
            return "handler_home";
        }
        if (userDto == Long.parseLong("2")) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("schedules", timeTableService.lecturersSchedulesForThisWeek(authentication.getName()));
            return "lecturer_home";
        }
        if (userDto == Long.parseLong("1")) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("schedules", timeTableService.studentsSchedulesForThisWeek(authentication.getName()));
            return "student_home";
        }
        return null;
    }
}
