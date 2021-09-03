package com.example.findmylecture.repository;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTable, Long> {


    @Query("from TimeTable t where t.room.roomId=:roomId")
    List<TimeTable> findByRoomId(Long roomId);

    @Query("select b.timeTables from Batch b where b.batchId=:batchId")
    List<TimeTable> findNumberOfBatchesOfTheSession(Long batchId);

    @Query("select t.batches from TimeTable t where t.timeTableId=:timetableId")
    List<Batch> getBatchesOfTimeTable(Long timetableId);

    @Query("select t.module.moduleName from TimeTable t where t.timeTableId=:timetableId")
    String getModuleNameOfTimetable(Long timetableId);

    @Query("select t.module from TimeTable t where t.timeTableId=:timetableId")
    Module getModule(Long timetableId);

    @Query("from TimeTable t where t.module.moduleName like:keyword")
    List<TimeTable> findByKeyword(@Param("keyword") String keyword);

    @Query("from TimeTable t where t.room.roomId=:roomId and t.Date=:date")
    List<TimeTable> findByClassroomAndDate(Long roomId, Date date);

    @Query("from TimeTable t where t.Date=:date")
    List<TimeTable> findBatchesByDate(Date date);

    @Query("select b.timeTables from Batch b where b.batchId=:batchId")
    List<TimeTable> findSchedulesForBatch(Long batchId);

    @Query("from TimeTable t where t.Date=:today")
    List<TimeTable> schedulesForToday(Date today);

    @Query("from TimeTable t where t.module.moduleId=:moduleId and t.Date=:date")
    List<TimeTable> schedulesForModuleAndDate(Long moduleId, Date date);
}
