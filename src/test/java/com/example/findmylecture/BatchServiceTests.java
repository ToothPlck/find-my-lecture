package com.example.findmylecture;

import com.example.findmylecture.dto.BatchDto;
import com.example.findmylecture.repository.BatchRepo;
import com.example.findmylecture.service.BatchService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BatchServiceTests {

    @Autowired
    private BatchService batchService;
    @Autowired
    private BatchRepo batchRepo;

    Long batchId;

    @Test
    @Order(1)
    public void testAddBatch() throws Exception {
        BatchDto batchDto = new BatchDto();
        batchDto.setBatchCode("testBatchCode");
        batchDto.setBatchPeriod(12);
        batchDto.setIntakeDate("2021-11-05");

        batchService.saveBatch(batchDto);
        batchId = batchRepo.findBatchIdByBatchCode("testBatchCode");

        assertNotNull(batchId);
        System.out.println("[TEST] Adding new batch [PASSED]");
    }

    @Test
    @Order(2)
    public void testAddBatchWithSameBatchCode() {
        BatchDto batchDto = new BatchDto();
        batchDto.setBatchCode("testBatchCode");
        batchDto.setBatchPeriod(12);
        batchDto.setIntakeDate("2021-11-05");

        boolean isTrue = false;

        try {
            batchService.saveBatch(batchDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("Another batch with the entered batch code already exists in the system! Please try again with a different batch code."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add a batch with an existing batch code [PASSED]");
    }

    @Test
    @Order(3)
    public void getAllBatches() {
        List<BatchDto> batchDtoList = batchService.findAllAssignableBatches();

        boolean isTrue = batchDtoList.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all batches [PASSED]");
    }
//
//    @Test
//    @Order(4)
//    public void getBatchByBatchId() {
//        BatchDto batchDto = batchService.findBatchByBatchId(batchId);
//
//        boolean isTrue = batchDto != null;
//
//        assertTrue(isTrue);
//        System.out.println("[TEST] Get batch by batch Id [PASSED]");
//    }

    @Test
    @Order(5)
    public void deleteBatch() {
        batchService.deleteBatchByBatchId(batchId);

        List<BatchDto> batchDtoList = batchService.findAllAssignableBatches();

        boolean isTrue = true;

        for (BatchDto batchDto : batchDtoList) {
            if (batchDto.getBatchId().equals(batchId)) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);
        System.out.println("[TEST] Delete batch by batch id [PASSED]");
    }
}
