package com.example.findmylecture.dto;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.TimeTable;
import com.example.findmylecture.model.User;

import java.util.List;

public class ModuleDto {
    private Long moduleId;
    private String moduleName;
    private String moduleDescription;
    private User user;
    List<Batch> batches;
    List<TimeTable> timeTables;

    public ModuleDto() {
    }

    public ModuleDto(Long moduleId,
                     String moduleName,
                     String moduleDescription,
                     User user,
                     List<Batch> batches,
                     List<TimeTable> timeTables) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.user = user;
        this.batches = batches;
        this.timeTables = timeTables;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public List<TimeTable> getTimeTable() {
        return timeTables;
    }

    public void setTimeTable(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
