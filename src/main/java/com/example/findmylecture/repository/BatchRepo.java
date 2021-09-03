package com.example.findmylecture.repository;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepo extends JpaRepository<Batch, Long> {

    @Query("from Batch b where b.batchCode=:batchCode")
    List<Batch> findByBatchCode(String batchCode);
}
