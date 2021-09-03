package com.example.findmylecture.dto;

import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.TimeTable;
import com.example.findmylecture.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchDto {
    private Long batchId;
    private String batchCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String intakeDate;
    private Integer batchPeriod;

    List<Module> modules;
    List<User> users;
    List<TimeTable> timeTables;

    public BatchDto() {
    }

    public BatchDto(Long batchId,
                    String batchCode,
                    String intakeDate,
                    Integer batchPeriod,
                    List<Module> modules,
                    List<User> users,
                    List<TimeTable> timeTables) {
        this.batchId = batchId;
        this.batchCode = batchCode;
        this.intakeDate = intakeDate;
        this.batchPeriod = batchPeriod;
        this.modules = modules;
        this.users = users;
        this.timeTables = timeTables;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public Integer getBatchPeriod() {
        return batchPeriod;
    }

    public void setBatchPeriod(Integer batchPeriod) {
        this.batchPeriod = batchPeriod;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
