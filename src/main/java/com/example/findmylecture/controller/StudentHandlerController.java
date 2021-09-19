package com.example.findmylecture.controller;

import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/handler/")
@PreAuthorize("hasAuthority('4')")
public class StudentHandlerController {

    @Autowired
    private UserService userService;
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private BatchService batchService;


    //=========================================================================================================Home page
    @GetMapping("home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.schedulesForToday());
        return "handler_home";
    }

    //==========================================================================================================Register
    @GetMapping("register")
    public String register(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("userForm", new UserDto());
        model.addAttribute("batches", batchService.findAllAssignableBatches());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("userForm") UserDto userDto, Model model, Authentication authentication) throws Exception {
        try {
            userService.saveStudent(userDto);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("batches", batchService.findAllAssignableBatches());
            model.addAttribute("error", "");
            model.addAttribute("success", "Student registered successfully!");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("batches", batchService.findAllAssignableBatches());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
        }
        return "handler_register";
    }

    //======================================================================================================view account
    @GetMapping("view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_view_account";
    }


    //====================================================================================================update account
    @GetMapping("update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_update_account";
    }

    @PostMapping("update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Model model, Authentication authentication) {
        try {
            userService.updateProfile(userDto, authentication.getName());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Account updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_update_account";
        }
        return "handler_view_account";
    }

    //==================================================================================================================
    //=====================================================================================================view students
    @GetMapping("view/students")
    public String viewStudents(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("students", userService.findAllStudents());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_view_students";
    }

    //====================================================================================================update student
    @GetMapping("update/student/{username}")
    public String updateStudent(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("student", userService.findByUsername(username));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_update_student";
    }

    @PostMapping("update/student/{username}")
    public String updateStudent(@PathVariable(value = "username") String username, UserDto userDto, Authentication authentication, Model model) {
        try {
            userService.updateStudent(userDto, username);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("students", userService.findAllStudents());
            model.addAttribute("error", "");
            model.addAttribute("success", "The student has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("student", userService.findByUsername(username));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_update_student";
        }
        return "handler_view_students";
    }

    //====================================================================================update/de-assign student batch
    @GetMapping("update/student/batch/{username}")
    public String updateStudentBatch(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("studentBatch", userService.findStudentsBatch(username));
        model.addAttribute("allBatches", batchService.findAllAssignableBatches());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_update_student_batch";
    }

    @PostMapping("update/student/batch/{username}")
    public String updateStudentBatch(@PathVariable(value = "username") String username, UserDto userDto, Model model, Authentication authentication) {
        try {
            userService.updateStudentBatch(username, userDto);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("student", userService.findByUsername(username));
            model.addAttribute("error", "");
            model.addAttribute("success", "The student has been assigned to the new batch successfully!");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("studentBatch", userService.findStudentsBatch(username));
            model.addAttribute("allBatches", batchService.findAllAssignableBatches());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_update_student_batch";
        }
        return "handler_update_student";
    }

    @GetMapping("update/student/batch/{username}/de-assign")
    public String deAssignStudentBatch(@PathVariable(value = "username") String username) {
        userService.deAssignStudentBatch(username);
        return "redirect:/handler/update/student/batch/" + username;
    }

    //====================================================================================================delete student
    @RequestMapping("delete/student/{username}")
    public String deleteStudent(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        try {
            userService.deleteUserByUsername(username);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("students", userService.findAllStudents());
            model.addAttribute("error", "");
            model.addAttribute("success", username + " has been deleted successfully");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("students", userService.findAllStudents());
            model.addAttribute("success", "");
            model.addAttribute("error", exception.getMessage());
        }
        return "handler_view_students";
    }


    //==================================================================================================================
    //================================================================================================create new lecture
    @GetMapping("add/timetable")
    public String addTimeTable(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timetableForm", new TimeTableDto());
        model.addAttribute("rooms", roomService.getRoomsForTimeTable());
        model.addAttribute("modules", moduleService.getModulesForTimeTable());
        model.addAttribute("batches", batchService.getBatchesForTimeTable());
        return "handler_add_timetable";
    }

    @PostMapping("add/timetable")
    public String addTimeTable(@ModelAttribute("timetableForm") TimeTableDto timeTableDto, Model model, Authentication authentication) {
        try {
            timeTableService.save(timeTableDto);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timetableForm", new TimeTableDto());
            model.addAttribute("rooms", roomService.getRoomsForTimeTable());
            model.addAttribute("modules", moduleService.getModulesForTimeTable());
            model.addAttribute("batches", batchService.getBatchesForTimeTable());
            model.addAttribute("error", "");
            model.addAttribute("success", "Schedule added successfully");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timetableForm", new TimeTableDto());
            model.addAttribute("rooms", roomService.getRoomsForTimeTable());
            model.addAttribute("modules", moduleService.getModulesForTimeTable());
            model.addAttribute("batches", batchService.getBatchesForTimeTable());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
        }
        return "handler_add_timetable";
    }

    //===================================================================================================view time table
    @GetMapping("view/timetable")
    public String viewTimeTable(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timeTable", timeTableService.getTimeTable());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_view_timetable";
    }

    //=================================================================================================search time table
    @GetMapping("search/timetable/{search}")
    public String searchTimeTable(@PathVariable(value = "search")String search, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timeTable", timeTableService.searchTimetable(search));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_view_timetable";
    }

    //=================================================================================================update time table
    @GetMapping("update/timetable/{timetableId}")
    public String updateTimeTable(@PathVariable(value = "timetableId") Long timetableId, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timetable", timeTableService.findTimetableByTimetableId(timetableId));
        model.addAttribute("rooms", roomService.getRoomsForTimeTable());
        model.addAttribute("modules", moduleService.getModulesForTimeTable());
        model.addAttribute("batches", batchService.getBatchesForTimeTable());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_update_timetable";
    }

    @PostMapping("update/timetable/{timetableId}")
    public String updateTimeTable(@PathVariable(value = "timetableId") Long timetableId, TimeTableDto timeTableDto, Authentication authentication, Model model) {
        try {
            timeTableService.updateTimetable(timetableId, timeTableDto);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timeTable", timeTableService.getTimeTable());
            model.addAttribute("error", "");
            model.addAttribute("success", "Schedule updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timetable", timeTableService.findTimetableByTimetableId(timetableId));
            model.addAttribute("rooms", roomService.getRoomsForTimeTable());
            model.addAttribute("modules", moduleService.getModulesForTimeTable());
            model.addAttribute("batches", batchService.getBatchesForTimeTable());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_update_timetable";
        }
        return "handler_view_timetable";
    }

    //=================================================================================================delete time table
    @RequestMapping("delete/timetable/{timetableId}")
    public String deleteTimeTable(@PathVariable(value = "timetableId") Long timetableId, Model model, Authentication authentication) {
        try {
            timeTableService.deleteTimeTable(timetableId);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timeTable", timeTableService.getTimeTable());
            model.addAttribute("error", "");
            model.addAttribute("success", "Schedule deleted successfully");
        } catch (Exception exception) {
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("timeTable", timeTableService.getTimeTable());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_view_timetable";
        }
        return "handler_view_timetable";
    }

}
