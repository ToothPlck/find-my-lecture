package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.ModuleDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mailHandler.EmailService;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.TimeTable;
import com.example.findmylecture.repository.ModuleRepo;
import com.example.findmylecture.repository.TimeTableRepo;
import com.example.findmylecture.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImplementation implements ModuleService {

    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TimeTableRepo timeTableRepo;

    @Override
    public void save(ModuleDto moduleDto) throws Exception {
        List<Module> modulesWithModulesName = moduleRepo.findModuleByModuleName(moduleDto.getModuleName());
        if (modulesWithModulesName.size() != 0) {
            throw new Exception("Another module with the entered module name already exists in the system! Please try again with a different module name.");
        } else if (moduleDto.getUser() != null) {
            List<Module> lecturerModules = moduleRepo.findModulesByUsername(moduleDto.getUser().getUsername());
            if (lecturerModules.size() > 8) {
                throw new Exception("The lecturer assigned for the created module has been already assigned the max limit of modules per lecturer! " +
                        "Please try again with another lecturer, or reduce the number of modules assigned to the selected lecturer.");
            }
        }
        Module module = new Module();

        module.setModuleId(moduleDto.getModuleId());
        module.setModuleName(moduleDto.getModuleName());
        module.setModuleDescription(moduleDto.getModuleDescription());

        module.setUser(moduleDto.getUser());

        moduleRepo.save(module);
    }

    @Override
    public List<ModuleDto> getAllModules() {
        List<ModuleDto> moduleDtoList = new ArrayList<>();

        for (Module module : moduleRepo.findAll()) {
            ModuleDto moduleDto = new ModuleDto();

            moduleDto.setModuleId(module.getModuleId());
            moduleDto.setModuleName(module.getModuleName());
            moduleDto.setModuleDescription(module.getModuleDescription());

            moduleDto.setUser(module.getUser());
            moduleDto.setBatches(module.getBatches());

            moduleDtoList.add(moduleDto);
        }
        return moduleDtoList;
    }

    @Override
    public List<ModuleDto> getModulesForBatch() {
        List<ModuleDto> moduleDtoList = new ArrayList<>();

        for (Module module : moduleRepo.findAll()) {
            if (module.getUser() != null) {
                ModuleDto moduleDto = new ModuleDto();

                moduleDto.setModuleId(module.getModuleId());
                moduleDto.setModuleName(module.getModuleName());
                moduleDto.setModuleDescription(module.getModuleDescription());

                moduleDto.setUser(module.getUser());
                moduleDto.setBatches(module.getBatches());

                moduleDtoList.add(moduleDto);
            }
        }
        return moduleDtoList;
    }

    @Override
    public Module findById(Long moduleId) {
        return moduleRepo.getById(moduleId);
    }

    @Override
    public ModuleDto findModuleByModuleId(Long moduleId) {
        ModuleDto moduleDto = new ModuleDto();
        Module module = moduleRepo.getById(moduleId);

        moduleDto.setModuleId(module.getModuleId());
        moduleDto.setModuleName(module.getModuleName());
        moduleDto.setModuleDescription(module.getModuleDescription());
        moduleDto.setBatches(module.getBatches());
        moduleDto.setUser(module.getUser());

        return moduleDto;
    }

    @Override
    public void deleteModuleByModuleId(Long moduleId) throws Exception {
        Module module = moduleRepo.findModuleByModuleId(moduleId);

        List<TimeTable> schedulesWithModule = timeTableRepo.schedulesForModule(module.getModuleId());
        if (schedulesWithModule.size() > 0) {
            for (TimeTable schedule : schedulesWithModule) {
                if ((LocalDate.parse(schedule.getDate().toString()).isAfter(LocalDate.now()))
                        ||
                        LocalDate.parse(schedule.getDate().toString()).isEqual(LocalDate.now())) {
                    throw new Exception("The module has upcoming schedules in the time table! Please update those schedules before removing the module.");
                }
            }
        }
        else if(module.getBatches().size() != 0){
            throw new Exception("The module is assigned to " + module.getBatches().size() + " batches. Please update the batches before removing the module.");
        }
        else {
            moduleRepo.deleteById(moduleId);
            if (module.getUser() != null) {
                emailService.removeModuleFromLecturer(module.getUser().getEmail(), module.getModuleName());
            }
        }

    }

    @Override
    public void updateModule(Long moduleId, ModuleDto moduleDto) throws Exception {
        List<Module> modulesWithModulesName = moduleRepo.findModuleByModuleName(moduleDto.getModuleName());
        if (modulesWithModulesName.size() != 0) {
            for (Module moduleWithModuleName : modulesWithModulesName) {
                if (!moduleWithModuleName.getModuleId().equals(moduleId)) {
                    throw new Exception("Another module with the entered module name already exists in the system! " +
                            "Please try again with a different module name.");
                }
            }
        }

        Module module = new Module();

        Optional<Module> optionalModule = moduleRepo.findById(moduleId);
        if (optionalModule.isPresent()) {
            module = optionalModule.get();
        }

        module.setModuleName(moduleDto.getModuleName());
        module.setModuleDescription(moduleDto.getModuleDescription());

        moduleRepo.save(module);
    }

    @Override
    public void updateModuleLecturer(Long moduleId, ModuleDto moduleDto) throws Exception {
        Module module = new Module();

        Optional<Module> optionalModule = moduleRepo.findById(moduleId);
        if (optionalModule.isPresent()) {
            module = optionalModule.get();
        }
        if (moduleDto.getUser() != null) {
            if (moduleDto.getUser() != module.getUser()) {
                List<Module> lecturerModules = moduleRepo.findModulesByUsername(moduleDto.getUser().getUsername());
                if (lecturerModules.size() > 8) {
                    throw new Exception("The lecturer assigned for the module has been already assigned the max limit of modules per lecturer! " +
                            "Please try again with another lecturer, or reduce the number of modules assigned to the selected lecturer.");
                }
            }
        }

        module.setUser(moduleDto.getUser());

        moduleRepo.save(module);
    }

    @Override
    public void deAssignLecturerFromModule(Long moduleId, String username) throws Exception {
        Module module = moduleRepo.getById(moduleId);

        List<TimeTable> schedulesWithModule = timeTableRepo.schedulesForModule(module.getModuleId());
        if (schedulesWithModule.size() > 0) {
            for (TimeTable schedule : schedulesWithModule) {
                if ((LocalDate.parse(schedule.getDate().toString()).isAfter(LocalDate.now()))
                        ||
                        LocalDate.parse(schedule.getDate().toString()).isEqual(LocalDate.now())) {
                    throw new Exception("The module has upcoming schedules in the time table! Please update those schedules before de-assigning the lecturer.");
                }
            }
        } else {
            emailService.deAssignLecturerFromModule(module.getUser().getEmail(), module.getModuleName());
            module.setUser(null);
        }
        moduleRepo.save(module);

//        if (module.getUser() != null) {
//            emailService.deAssignLecturerFromModule(module.getUser().getEmail(), module.getModuleName());
//            module.setUser(null);
//            moduleRepo.save(module);
//        }
    }

    @Override
    public List<ModuleDto> getModulesForTimeTable() {
        List<ModuleDto> modules = new ArrayList<>();

        for (Module module : moduleRepo.findAll()) {
            ModuleDto moduleDto = new ModuleDto();

            moduleDto.setModuleId(module.getModuleId());
            moduleDto.setModuleName(module.getModuleName());

            modules.add(moduleDto);
        }
        return modules;
    }

    @Override
    public void deAssignLecturerFromModules(String username) throws Exception {

        List<Module> modules = moduleRepo.findByUsername(username);
        if (modules.size() != 0) {
            for (Module module : modules) {
                List<TimeTable> schedulesWithModule = timeTableRepo.schedulesForModule(module.getModuleId());
                if (schedulesWithModule.size() > 0) {
                    for (TimeTable schedule : schedulesWithModule) {
                        if ((LocalDate.parse(schedule.getDate().toString()).isAfter(LocalDate.now()))
                                ||
                                LocalDate.parse(schedule.getDate().toString()).isEqual(LocalDate.now())) {
                            throw new Exception("The lecturer has upcoming schedules in the time table! Please update those schedules before removing the lecturer.");
                        }
                    }
                } else {
                    emailService.deAssignLecturerFromModule(module.getUser().getEmail(), module.getModuleName());
                    module.setUser(null);
                }
                moduleRepo.save(module);
            }
        }
    }
}
