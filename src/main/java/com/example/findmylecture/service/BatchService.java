package com.example.findmylecture.service;

import com.example.findmylecture.dto.BatchDto;

import java.util.List;

public interface BatchService {

    void saveBatch(BatchDto batchDto) throws Exception;

    List<BatchDto> findAllBatches();

    BatchDto findBatchByBatchId(Long batchId);

    void deleteBatchByBatchId(Long batchId);

    void updateBatch(Long batchId, BatchDto batchDto) throws Exception;

    void updateBatchModules(Long batchId, BatchDto batchDto) throws Exception;

    void deAssignModuleFromBatch(Long batchId, Long moduleId);

    List<BatchDto> getBatchesForTimeTable();

    List<BatchDto> findAllAssignableBatches();
}
