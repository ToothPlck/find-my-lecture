package com.example.findmylecture.controller;

import com.example.findmylecture.dto.BatchDto;
import com.example.findmylecture.dto.ModuleDto;
import com.example.findmylecture.dto.RoomDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.service.*;
import com.example.findmylecture.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

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
    @GetMapping("/admin/home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("schedules", timeTableService.schedulesForToday());
        return "admin_home";
    }


    //==========================================================================================================Register
    @GetMapping("/admin/register")
    public String register(Model model, Authentication authentication) {
        model.addAttribute("userForm", new UserDto());
        model.addAttribute("userRoles", roleService.getStaffRoles());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_register";
    }

    @PostMapping("/admin/register")
    public String register(@ModelAttribute("userForm") UserDto userDto, Model model, Authentication authentication) throws Exception {
        try {
            userService.save(userDto);
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("userRoles", roleService.getStaffRoles());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "User has been registered");
        } catch (Exception exception) {
            model.addAttribute("userForm", new UserDto());
            model.addAttribute("userRoles", roleService.getStaffRoles());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_register";
        }
        return "admin_register";
    }


    //======================================================================================================view account
    @GetMapping("/admin/view/account")
    public String viewAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_view_account";
    }

    //====================================================================================================update account
    @GetMapping("/admin/update/account")
    public String updateAccount(Model model, Authentication authentication) {
        model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_account";
    }

    @PostMapping("/admin/update/account")
    public String updateAccount(@ModelAttribute("loggedUser") UserDto userDto, Authentication authentication, Model model) throws Exception{
        try {
            userService.updateProfile(userDto, authentication.getName());
            model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Account updated successfully!");
        }
        catch (Exception exception){
            model.addAttribute("loggedUser", userService.updatable(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_account";
        }
        return "admin_view_account";
    }

    //==================================================================================================================
    //====================================================================================================view lecturers
    @GetMapping("/admin/view/lecturers")
    public String viewLecturers(Model model, Authentication authentication) {
        model.addAttribute("lecturers", userService.findAllLecturers());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_view_lecturers";
    }

    //===================================================================================================update lecturer
    @GetMapping("/admin/update/lecturer/{username}")
    public String updateLecturer(@PathVariable(value = "username") String username, Model model, Authentication authentication) {
        model.addAttribute("lecturer", userService.findByUsername(username));
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_lecturer";
    }

    @PostMapping("/admin/update/lecturer/{username}")
    public String updateLecturer(@PathVariable(value = "username") String username, UserDto userDto, Model model, Authentication authentication) throws Exception {
        try {
            userService.updateLecturer(username, userDto);
            model.addAttribute("lecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "User has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("lecturer", userService.findByUsername(username));
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_lecturer";
        }
        return "admin_view_lecturers";
    }

    //===================================================================================================delete lecturer
    @RequestMapping("/admin/delete/lecturer/{username}")
    public String deleteLecturer(@PathVariable(value = "username") String username, Model model, Authentication authentication) throws Exception {
        try {
            moduleService.deAssignLecturerFromModules(username);
            userService.deleteUserByUsername(username);
            model.addAttribute("lecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "User has been deleted successfully!");
        } catch (Exception exception) {
            model.addAttribute("lecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_view_lecturers";
        }
        return "admin_view_lecturers";
    }

    //==================================================================================================================
    //==========================================================================================================Add room
    @GetMapping("/admin/add/room")
    public String addRoom(Model model, Authentication authentication) {
        model.addAttribute("roomForm", new RoomDto());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_add_room";
    }

    @PostMapping("/admin/add/room")
    public String addRoom(@ModelAttribute("roomForm") RoomDto roomDto, Authentication authentication, Model model) throws Exception {
        try {
            roomService.save(roomDto);
            model.addAttribute("rooms", roomService.findAllRooms());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Classroom added successfully!");
        } catch (Exception exception) {
            model.addAttribute("roomForm", new RoomDto());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_add_room";
        }
        return "admin_view_rooms";
    }

    //========================================================================================================view rooms
    @GetMapping("/admin/view/rooms")
    public String viewRooms(Model model, Authentication authentication) {
        model.addAttribute("rooms", roomService.findAllRooms());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_view_rooms";
    }

    //=======================================================================================================update room
    @GetMapping("/admin/update/room/{roomId}")
    public String updateRoom(@PathVariable(value = "roomId") Long roomId, Model model, Authentication authentication) {
        model.addAttribute("room", roomService.findByRoomId(roomId));
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_rooms";
    }

    @PostMapping("/admin/update/room/{roomId}")
    public String updateRoom(@PathVariable(value = "roomId") Long roomId, RoomDto roomDto, Authentication authentication, Model model) throws Exception {
        try {
            roomService.updateRoom(roomId, roomDto);
            model.addAttribute("rooms", roomService.findAllRooms());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Classroom updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("room", roomService.findByRoomId(roomId));
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_rooms";
        }
        return "admin_view_rooms";
    }
    //=======================================================================================================delete room

    @GetMapping("/admin/delete/room/{roomId}")
    public String deleteRoom(@PathVariable(value = "roomId") Long roomId, Authentication authentication, Model model) throws Exception {
        try {
            timeTableService.updateWhereRoom(roomId);
            roomService.deleteRoomByRoomId(roomId);
            model.addAttribute("rooms", roomService.findAllRooms());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The classroom has been deleted successfully!");
        } catch (Exception exception) {
            model.addAttribute("rooms", roomService.findAllRooms());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_view_rooms";
        }
        return "admin_view_rooms";
    }

    //==================================================================================================================
    //=========================================================================================================add batch
    @GetMapping("/admin/add/batch")
    public String addBatch(Model model, Authentication authentication) {
        model.addAttribute("batchForm", new BatchDto());
        model.addAttribute("modules", moduleService.getModulesForBatch());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_add_batch";
    }

    @PostMapping("/admin/add/batch")
    public String addBatch(@ModelAttribute("batchForm") BatchDto batchDto, Model model, Authentication authentication) throws Exception {
        try {
            batchService.saveBatch(batchDto);
            model.addAttribute("batches", batchService.findAllBatches());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Batch added successfully!");
        } catch (Exception exception) {
            model.addAttribute("batchForm", new BatchDto());
            model.addAttribute("modules", moduleService.getModulesForBatch());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_add_batch";
        }
        return "admin_view_batches";
    }

    //======================================================================================================view batches
    @GetMapping("/admin/view/batches")
    public String viewBatches(Model model, Authentication authentication) {
        model.addAttribute("batches", batchService.findAllBatches());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_view_batches";
    }

    //======================================================================================================update batch
    @GetMapping("/admin/update/batch/{batchId}")
    public String updateBatch(@PathVariable(value = "batchId") Long batchId, Model model, Authentication authentication) {
        model.addAttribute("batch", batchService.findBatchByBatchId(batchId));
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_batch";
    }

    @PostMapping("/admin/update/batch/{batchId}")
    public String updateBatch(@PathVariable(value = "batchId") Long batchId, BatchDto batchDto, Authentication authentication, Model model) throws Exception {
        try {
            batchService.updateBatch(batchId, batchDto);
            model.addAttribute("batches", batchService.findAllBatches());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The batch has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("batch", batchService.findBatchByBatchId(batchId));
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            System.out.println(exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_batch";
        }
        return "admin_view_batches";
    }

    //==============================================================================================update batch modules
    @GetMapping("/admin/update/batch/modules/{batchId}")
    public String updateBatchModules(@PathVariable(value = "batchId") Long batchId, Model model, Authentication authentication) {
        model.addAttribute("batchModules", batchService.findBatchByBatchId(batchId));
        model.addAttribute("allModules", moduleService.getModulesForBatch());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_batch_modules";
    }

    @PostMapping("/admin/update/batch/modules/{batchId}")
    public String updateBatchModules(@PathVariable(value = "batchId") Long batchId, BatchDto batchDto, Authentication authentication, Model model) throws Exception {
        try {
            batchService.updateBatchModules(batchId, batchDto);
            model.addAttribute("batchModules", batchService.findBatchByBatchId(batchId));
            model.addAttribute("allModules", moduleService.getModulesForBatch());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The modules for the batch has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("batchModules", batchService.findBatchByBatchId(batchId));
            model.addAttribute("allModules", moduleService.getModulesForBatch());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_batch_modules";
        }
        return "admin_update_batch_modules";
    }

    @GetMapping("/admin/update/{batchId}/modules/{moduleId}/de-assign")
    public String deAssignModule(@PathVariable(value = "moduleId") Long moduleId, @PathVariable(value = "batchId") Long batchId) {
        batchService.deAssignModuleFromBatch(batchId, moduleId);
        return "redirect:/admin/update/batch/modules/" + batchId;
    }

    //======================================================================================================delete batch
    @GetMapping("/admin/delete/batch/{batchId}")
    public String deleteBatch(@PathVariable(value = "batchId") Long batchId, Model model, Authentication authentication) throws Exception {
        try {
            userService.removeBatchFromStudents(batchId);
            timeTableService.removeTimetable(batchId);
            batchService.deleteBatchByBatchId(batchId);
            model.addAttribute("batches", batchService.findAllBatches());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The batch has been deleted successfully");
        } catch (Exception exception) {
            model.addAttribute("batches", batchService.findAllBatches());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_view_batches";
        }
        return "admin_view_batches";
    }

    //==================================================================================================================
    //========================================================================================================Add module
    @GetMapping("/admin/add/module")
    public String addModule(Model model, Authentication authentication) {
        model.addAttribute("moduleForm", new ModuleDto());
        model.addAttribute("lecturer", userService.findAllLecturers());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_add_module";
    }

    @PostMapping("/admin/add/module")
    public String addModule(@ModelAttribute("moduleForm") ModuleDto moduleDto, Model model, Authentication authentication) throws Exception {
        try {
            moduleService.save(moduleDto);
            model.addAttribute("modules", moduleService.getAllModules());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "Modules added successfully!");
        } catch (Exception exception) {
            model.addAttribute("moduleForm", new ModuleDto());
            model.addAttribute("lecturer", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_add_module";
        }
        return "admin_view_modules";
    }

    //======================================================================================================view modules
    @GetMapping("/admin/view/modules")
    public String viewModules(Model model, Authentication authentication) {
        model.addAttribute("modules", moduleService.getAllModules());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_view_modules";
    }

    //=====================================================================================================update module
    @GetMapping("admin/update/module/{moduleId}")
    public String updateModule(@PathVariable(value = "moduleId") Long moduleId, Model model, Authentication authentication) {
        model.addAttribute("module", moduleService.findModuleByModuleId(moduleId));
        model.addAttribute("lecturers", userService.findAllLecturers());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_module";
    }

    @PostMapping("/admin/update/module/{moduleId}")
    public String updateModule(@PathVariable(value = "moduleId") Long moduleId, ModuleDto moduleDto, Model model, Authentication authentication) throws Exception {
        try {
            moduleService.updateModule(moduleId, moduleDto);
            model.addAttribute("modules", moduleService.getAllModules());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The module has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("module", moduleService.findModuleByModuleId(moduleId));
            model.addAttribute("lecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_module";
        }
        return "admin_view_modules";
    }

    @GetMapping("admin/update/{moduleId}/lecturer/{username}/de-assign")
    public String deAssignLecturer(@PathVariable(value = "moduleId") Long moduleId, @PathVariable(value = "username") String username, Authentication authentication, Model model) {
        moduleService.deAssignLecturerFromModule(moduleId, username);
        model.addAttribute("module", moduleService.findModuleByModuleId(moduleId));
        model.addAttribute("lecturers", userService.findAllLecturers());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_module";
    }

    @GetMapping("/admin/update/module/lecturer/{moduleId}")
    public String updateModuleLecturer(@PathVariable(value = "moduleId") Long moduleId, Model model, Authentication authentication) {
        model.addAttribute("moduleLecturer", moduleService.findModuleByModuleId(moduleId));
        model.addAttribute("allLecturers", userService.findAllLecturers());
        model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        return "admin_update_module_lecturer";
    }

    @PostMapping("/admin/update/module/lecturer/{moduleId}")
    public String updateModuleLecturer(@PathVariable(value = "moduleId") Long moduleId, ModuleDto moduleDto, Model model, Authentication authentication) throws Exception {
        try {
            moduleService.updateModuleLecturer(moduleId, moduleDto);
            model.addAttribute("module", moduleService.findModuleByModuleId(moduleId));
            model.addAttribute("lecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The lecturer for the module has been updated successfully!");
        } catch (Exception exception) {
            model.addAttribute("moduleLecturer", moduleService.findModuleByModuleId(moduleId));
            model.addAttribute("allLecturers", userService.findAllLecturers());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_update_module_lecturer";
        }
        return "admin_update_module";
    }

    //=====================================================================================================delete module
    @GetMapping("/admin/delete/module/{moduleId}")
    public String deleteModule(@PathVariable("moduleId") Long moduleId, Model model, Authentication authentication) throws Exception {
        try {
            moduleService.deleteModuleByModuleId(moduleId);
            model.addAttribute("modules", moduleService.getAllModules());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", "");
            model.addAttribute("success", "The module has been deleted successfully!");
        } catch (Exception exception) {
            model.addAttribute("modules", moduleService.getAllModules());
            model.addAttribute("loggedUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("success", "");
            return "admin_view_modules";
        }
        return "admin_view_modules";
    }
}