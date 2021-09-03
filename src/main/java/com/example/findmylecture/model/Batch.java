package com.example.findmylecture.model;

import javax.persistence.*;
import java.sql.Date;

import java.util.List;

@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    @Column(length = 50)
    private String batchCode;
    private Date intakeDate;
    private Integer batchPeriod;

    @OneToMany(mappedBy = "batch", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    List<User> users;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "module_batch",
            joinColumns = {@JoinColumn(name = "batch_id")},
            inverseJoinColumns = {@JoinColumn(name = "module_id")}
    )
    List<Module> modules;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "timeTable_Batch",
            joinColumns = {@JoinColumn(name = "batch_id")},
            inverseJoinColumns = {@JoinColumn(name = "timetable_id")}
    )
    List<TimeTable> timeTables;

    public Batch() {
    }

    public Batch(Long batchId,
                 String batchCode,
                 Date intakeDate,
                 Integer batchPeriod,
                 List<User> users,
                 List<TimeTable> timeTables,
                 List<Module> modules) {
        this.batchId = batchId;
        this.batchCode = batchCode;
        this.intakeDate = intakeDate;
        this.batchPeriod = batchPeriod;
        this.users = users;
        this.timeTables = timeTables;
        this.modules = modules;
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

    public Date getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Date intakeDate) {
        this.intakeDate = intakeDate;
    }

    public Integer getBatchPeriod() {
        return batchPeriod;
    }

    public void setBatchPeriod(Integer batchPeriod) {
        this.batchPeriod = batchPeriod;
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

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
