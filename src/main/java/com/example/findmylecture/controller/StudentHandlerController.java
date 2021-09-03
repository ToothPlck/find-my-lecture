package com.example.findmylecture.controller;

import com.example.findmylecture.dto.TimeTableDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mailHandler.EmailService;
import com.example.findmylecture.service.*;
import com.example.findmylecture.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentHandlerController {

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

    @Autowired
    private EmailService emailService;


    //=========================================================================================================Home page
    @GetMapping("/handler/home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.schedulesForToday());
        return "handler_home";
    }

    //==========================================================================================================Register
    @GetMapping("/handler/register")
    public String register(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("userForm", new UserDto());
        model.addAttribute("batches", batchService.findAllAssignableBatches());
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_register";
    }

    @PostMapping("/handler/register")
    public String register(@ModelAttribute("userForm") UserDto userDto, Model model, Authentication authentication) throws Exception {
        try {
            userService.saveStudent(userDto);
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("batches", batchService.findAllAssignableBatches());
            model.addAttribute("error", "");
            model.addAttribute("success", "Student registered successfully!");
        }
        catch (Exception exception){
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("batches", batchService.findAllAssignableBatches());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
        }
        return "handler_register";
    }

    //======================================================================================================view account
    @GetMapping("/handler/view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_view_account";
    }


    //====================================================================================================update account
    @GetMapping("/handler/update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "handler_update_account";
    }

    @PostMapping("/handler/update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Model model, Authentication authentication) throws Exception {
        try {
            userService.updateProfile(userDto, authentication.getName());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Account updated successfully!");
        }
        catch (Exception exception){
            model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "handler_update_account";
        }
        return "handler_view_account";
    }

    //==================================================================================================================
    //=====================================================================================================view students
    @GetMapping("/handler/view/students")
    public String viewStudents(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("students", userService.findAllStudents());
        return "handler_view_students";
    }

    //====================================================================================================update student
    @GetMapping("/handler/update/student/{username}")
    public String updateStudent(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("student", userService.findByUsername(username));
        return "handler_update_student";
    }

    @PostMapping("/handler/update/student/{username}")
    public String updateStudent(@PathVariable(value = "username") String username, UserDto userDto) {
        userService.updateStudent(userDto, username);
        return "redirect:/handler/view/students";
    }

    //====================================================================================update/de-assign student batch
    @GetMapping("/handler/update/student/batch/{username}")
    public String updateStudentBatch(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("studentBatch", userService.findStudentsBatch(username));
        model.addAttribute("allBatches", batchService.findAllBatches());
        return "handler_update_student_batch";
    }

    @PostMapping("/handler/update/student/batch/{username}")
    public String updateStudentBatch(@PathVariable(value = "username") String username, UserDto userDto) {
        userService.updateStudentBatch(username, userDto);
        return "redirect:/handler/update/student/" + username;
    }

    @GetMapping("/handler/update/student/batch/{username}/de-assign")
    public String deAssignStudentBatch(@PathVariable(value = "username") String username) {
        userService.deAssignStudentBatch(username);
        return "redirect:/handler/update/student/batch/" + username;
    }

    //====================================================================================================delete student
    @RequestMapping("/handler/delete/student/{username}")
    public String deleteStudent(@PathVariable(value = "username") String username, Model model) {
        try {
            userService.deleteUserByUsername(username);
            model.addAttribute("success", username + " has been deleted successfully");
        } catch (Exception exception) {
            model.addAttribute("error", username + " cannot be deleted. Try again later");
        }
        return "redirect:/handler/view/students";
    }


    //==================================================================================================================
    //================================================================================================create new lecture
    @GetMapping("/handler/add/timetable")
    public String addTimeTable(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timetableForm", new TimeTableDto());
        model.addAttribute("rooms", roomService.getRoomsForTimeTable());
        model.addAttribute("modules", moduleService.getModulesForTimeTable());
        model.addAttribute("batches", batchService.getBatchesForTimeTable());
        return "handler_add_timetable";
    }

    @PostMapping("/handler/add/timetable")
    public String addTimeTable(@ModelAttribute("timetableForm") TimeTableDto timeTableDto) throws Exception {
        timeTableService.save(timeTableDto);
        return "redirect:/handler/add/timetable";
    }

    //===================================================================================================view time table
    @GetMapping("/handler/view/timetable")
    public String viewTimeTable(Model model, Authentication authentication, String keyword) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timeTable", timeTableService.getTimeTable());
        return "handler_view_timetable";
    }

    //=================================================================================================search time table
    @GetMapping("/handler/search/timetable")
    public String searchTimeTable(Model model, Authentication authentication, HttpServletRequest request){
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        String keyword = request.getParameter("keyword");
        System.out.println(keyword+"\n\n\n\n\n\n"+request);
        model.addAttribute("timeTable", timeTableService.searchTimetable(keyword));
        return "handler_view_timetable";
    }

    //=================================================================================================update time table
    @GetMapping("/handler/update/timetable/{timetableId}")
    public String updateTimeTable(@PathVariable(value = "timetableId") Long timetableId, Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("timetable", timeTableService.findTimetableByTimetableId(timetableId));
        model.addAttribute("rooms", roomService.getRoomsForTimeTable());
        model.addAttribute("modules", moduleService.getModulesForTimeTable());
        model.addAttribute("batches", batchService.getBatchesForTimeTable());
        return "handler_update_timetable";
    }

    @PostMapping("/handler/update/timetable/{timetableId}")
    public String updateTimeTable(@PathVariable(value = "timetableId") Long timetableId, TimeTableDto timeTableDto) throws Exception {
        timeTableService.updateTimetable(timetableId, timeTableDto);
        return "redirect:/handler/view/timetable";
    }

    //=================================================================================================delete time table
    @RequestMapping("/handler/delete/timetable/{timetableId}")
    public String deleteTimeTable(@PathVariable(value = "timetableId") Long timetableId) {
        timeTableService.deleteTimeTable(timetableId);
        return "redirect:/handler/view/timetable";
    }

}
