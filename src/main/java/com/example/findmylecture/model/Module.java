package com.example.findmylecture.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;
    @Column(length = 50)
    private String moduleName;
    @Column(length = 200)
    private String moduleDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "module_batch",
            joinColumns = {@JoinColumn(name = "module_id")},
            inverseJoinColumns = {@JoinColumn(name = "batch_id")}
    )
    List<Batch> batches;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    List<TimeTable> timeTables;

    public Module() {
    }

    public Module(Long moduleId,
                  String moduleName,
                  String moduleDescription,
                  User user,
                  List<TimeTable> timeTables,
                  List<Batch> batches) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.user = user;
        this.timeTables = timeTables;
        this.batches = batches;
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

    public List<TimeTable> getTimeTable() {
        return timeTables;
    }

    public void setTimeTable(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

}
