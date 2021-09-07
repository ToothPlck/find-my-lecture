package com.example.findmylecture.service;

import com.example.findmylecture.dto.ModuleDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.model.Module;

import java.util.List;

public interface ModuleService {

    void save(ModuleDto moduleDto) throws Exception;

    List<ModuleDto> getAllModules();

    ModuleDto findModuleByModuleId(Long moduleId);

    void deleteModuleByModuleId(Long moduleId) throws Exception;

    void updateModule(Long moduleId, ModuleDto moduleDto) throws Exception;

    void updateModuleLecturer(Long moduleId, ModuleDto moduleDto) throws Exception;

    void deAssignLecturerFromModule(Long moduleId, String username) throws Exception;

    List<ModuleDto> getModulesForTimeTable();

    void deAssignLecturerFromModules(String username) throws Exception;

    List<ModuleDto> getModulesForBatch();
}
