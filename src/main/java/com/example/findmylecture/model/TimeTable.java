package com.example.findmylecture.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

@Entity
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeTableId;
    private Date Date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "timeTable_Batch",
            joinColumns = {@JoinColumn(name = "timetable_id")},
            inverseJoinColumns = {@JoinColumn(name = "batch_id")}
    )
    List<Batch> batches;

    public TimeTable() {
    }

    public TimeTable(Long timeTableId,
                     java.sql.Date date,
                     LocalTime startTime,
                     LocalTime endTime,
                     Room room,
                     Module module,
                     List<Batch> batches) {
        this.timeTableId = timeTableId;
        Date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.module = module;
        this.batches = batches;
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(Long timeTableId) {
        this.timeTableId = timeTableId;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
