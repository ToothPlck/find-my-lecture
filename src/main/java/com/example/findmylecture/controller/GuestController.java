package com.example.findmylecture.controller;

import com.example.findmylecture.service.*;
import com.example.findmylecture.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

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


    @GetMapping("/login")
    public String login(Model model) {
        try {
            model.addAttribute("error", "");
        } catch (Exception exception) {
            model.addAttribute("error", "The username and password did not match! Please try again.");
            return "login";
        }
        return "login";
    }
}
