package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.BatchDto;
import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.repository.BatchRepo;
import com.example.findmylecture.repository.ModuleRepo;
import com.example.findmylecture.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImplementation implements BatchService {

    @Autowired
    private BatchRepo batchRepo;
    @Autowired
    private ModuleRepo moduleRepo;

    @Override
    public void saveBatch(BatchDto batchDto) throws Exception {
        try {
            List<Batch> batchesWithBatchCode = batchRepo.findByBatchCode(batchDto.getBatchCode());
            if (batchesWithBatchCode.size() != 0) {
                throw new Exception("Another batch with the entered batch code already exists in the system! Please try again with a different batch code.");
            } else if (batchDto.getBatchPeriod() < 3 || batchDto.getBatchPeriod() > 60) {
                throw new Exception("A batches duration can be between minimum three (3) to maximum sixty (60) months!");
            } else if (LocalDate.parse(batchDto.getIntakeDate()).isBefore(LocalDate.now())) {
                throw new Exception("The selected Intake date for the batch is set in a previous date! Please select a future date for the Intake date.");
            } else if (batchDto.getModules() != null) {
                if (batchDto.getModules().size() > 16) {
                    throw new Exception("The batch has too many modules assigned to it! The max limit of modules for a batch is sixteen (16).");
                }
            }

            Batch batch = new Batch();

            batch.setBatchId(batchDto.getBatchId());
            batch.setBatchCode(batchDto.getBatchCode());
            batch.setIntakeDate(Date.valueOf(batchDto.getIntakeDate()));
            batch.setBatchPeriod(batchDto.getBatchPeriod());

            batch.setModules((batchDto.getModules()));

            batchRepo.save(batch);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    @Override
    public List<BatchDto> findAllBatches() {
        List<BatchDto> batchDtoList = new ArrayList<>();

        for (Batch batch : batchRepo.findAll()) {
            if (LocalDate.parse(batch.getIntakeDate().toString()).isAfter(LocalDate.now())) {
                BatchDto batchDto = new BatchDto();

                batchDto.setBatchId(batch.getBatchId());
                batchDto.setBatchCode(batch.getBatchCode());
                batchDto.setBatchPeriod(batch.getBatchPeriod());
                batchDto.setIntakeDate(batch.getIntakeDate().toString());
                batchDto.setModules(batch.getModules());

                batchDtoList.add(batchDto);
            }
        }
        return batchDtoList;
    }

    @Override
    public List<BatchDto> findAllAssignableBatches() {
        List<BatchDto> batchDtoList = new ArrayList<>();

        for (Batch batch : batchRepo.findAll()) {
            if (LocalDate.parse(batch.getIntakeDate().toString()).isAfter(LocalDate.now().minusMonths(3))) {
                BatchDto batchDto = new BatchDto();

                batchDto.setBatchId(batch.getBatchId());
                batchDto.setBatchCode(batch.getBatchCode());
                batchDto.setBatchPeriod(batch.getBatchPeriod());
                batchDto.setIntakeDate(batch.getIntakeDate().toString());
                batchDto.setModules(batch.getModules());

                batchDtoList.add(batchDto);
            }
        }
        return batchDtoList;
    }

    @Override
    public BatchDto findBatchByBatchId(Long batchId) {
        BatchDto batchDto = new BatchDto();
        Batch batch = batchRepo.getById(batchId);

        batchDto.setBatchId(batch.getBatchId());
        batchDto.setBatchCode(batch.getBatchCode());
        batchDto.setBatchPeriod(batch.getBatchPeriod());
        batchDto.setIntakeDate(batch.getIntakeDate().toString());
        batchDto.setModules(batch.getModules());

        return batchDto;
    }

    @Override
    public void deleteBatchByBatchId(Long batchId) {
        batchRepo.deleteById(batchId);
    }

    @Override
    public void updateBatch(Long batchId, BatchDto batchDto) throws Exception {
        try {
            List<Batch> batchesWithBatchCode = batchRepo.findByBatchCode(batchDto.getBatchCode());
            if (batchesWithBatchCode.size() != 0) {
                for (Batch batchWithBatchCode : batchesWithBatchCode) {
                    if (!batchId.equals(batchWithBatchCode.getBatchId())) {
                        throw new Exception("Another batch with the entered batch code already exists in the system! Please try again with a different batch code.");
                    }
                }
            }
            if (batchDto.getBatchPeriod() < 3 || batchDto.getBatchPeriod() > 48) {
                throw new Exception("A batches duration can be between minimum three (3) to maximum sixty (60) months!");
            }
            if (LocalDate.parse(batchDto.getIntakeDate()).isBefore(LocalDate.now())) {
                throw new Exception("The selected Intake date for the batch is set in a previous date! Please select a future date for the Intake date.");
            }

            Batch batch = new Batch();

            Optional<Batch> optionalBatch = batchRepo.findById(batchId);
            if (optionalBatch.isPresent()) {
                batch = optionalBatch.get();
            }

            batch.setBatchCode(batchDto.getBatchCode());
            batch.setBatchPeriod(batchDto.getBatchPeriod());
            batch.setIntakeDate(Date.valueOf(batchDto.getIntakeDate()));

            batchRepo.save(batch);
        } catch (Exception exception) {
            throw new Exception("An unexpected error occurred while updating the batch!");
        }

    }

    @Override
    public void updateBatchModules(Long batchId, BatchDto batchDto) throws Exception {
        if (batchDto.getModules().size() > 16) {
            throw new Exception("The batch has too many modules assigned to it! The max limit of modules for a batch is sixteen (16).");
        }
        if (batchDto.getModules().size() != 0) {
            Batch allModules = new Batch();

            Optional<Batch> batchRepoById = batchRepo.findById(batchId);
            if (batchRepoById.isPresent()) {
                allModules = batchRepoById.get();
            }

            //existing modules
            Batch batch = batchRepo.getById(batchId);
            Batch existingModules = new Batch();

            existingModules.setModules(batch.getModules());

            //new modules
            Batch updatedModules = new Batch();
            updatedModules.setModules(batchDto.getModules());

            //save all modules
            allModules.setModules(existingModules.getModules());
            allModules.setModules(updatedModules.getModules());

            batchRepo.save(allModules);
        }
    }

    @Override
    public void deAssignModuleFromBatch(Long batchId, Long moduleId) {
        Batch batch = new Batch();
        List<Module> existingModules = new ArrayList<>();

        Optional<Batch> optionalBatch = batchRepo.findById(batchId);
        if (optionalBatch.isPresent()) {
            batch = optionalBatch.get();
        }

        Batch getModules = batchRepo.getById(batchId);

        for (Module module : getModules.getModules()) {
            if (!module.getModuleId().equals(moduleId)) {
                module.setModuleId(module.getModuleId());

                existingModules.add(module);
            }
        }
        batch.setModules(existingModules);

        batchRepo.save(batch);
    }

    @Override
    public List<BatchDto> getBatchesForTimeTable() {
        List<BatchDto> batches = new ArrayList<>();

        for (Batch batch : batchRepo.findAll()) {
            BatchDto batchDto = new BatchDto();

            batchDto.setBatchId(batch.getBatchId());
            batchDto.setBatchCode(batch.getBatchCode());

            batches.add(batchDto);
        }
        return batches;
    }
}