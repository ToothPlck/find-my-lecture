package com.example.findmylecture.dto;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.Room;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public class TimeTableDto {
    private Long timeTableId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String Date;
    private String startTime;
    private String endTme;

    private Room room;
    private Module module;
    List<Batch> batches;

    public TimeTableDto() {
    }

    public TimeTableDto(Long timeTableId,
                        String date,
                        String startTime,
                        String endTme,
                        Room rooms,
                        Module modules,
                        List<Batch> batches) {
        this.timeTableId = timeTableId;
        this.Date = date;
        this.startTime = startTime;
        this.endTme = endTme;
        this.room = rooms;
        this.module = modules;
        this.batches = batches;
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(Long timeTableId) {
        this.timeTableId = timeTableId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTme() {
        return endTme;
    }

    public void setEndTme(String endTme) {
        this.endTme = endTme;
    }

    public Room getRooms() {
        return room;
    }

    public void setRooms(Room rooms) {
        this.room = rooms;
    }

    public Module getModules() {
        return module;
    }

    public void setModules(Module modules) {
        this.module = modules;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
