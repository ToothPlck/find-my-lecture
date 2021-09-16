package com.example.findmylecture;

import com.example.findmylecture.dto.ModuleDto;
import com.example.findmylecture.repository.ModuleRepo;
import com.example.findmylecture.service.ModuleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ModuleServiceTests {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleRepo moduleRepo;

    Long moduleId;

    @Test
    @Order(1)
    public void testAddModule() throws Exception {
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setModuleName("testModuleName");
        moduleDto.setModuleDescription("testModuleDescription");

        moduleService.save(moduleDto);
        moduleId = moduleRepo.findModuleIdByModuleName("testModuleName");

        assertNotNull(moduleId);
        System.out.println("[TEST] Adding new module [PASSED]");
    }

    @Test
    @Order(2)
    public void testAddModuleWithSameName() {
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setModuleName("testModuleName");
        moduleDto.setModuleDescription("testModuleDescription");

        boolean isTrue = false;

        try {
            moduleService.save(moduleDto);
        } catch (Exception exception) {
            if (exception.getMessage().equals("Another module with the entered module name already exists in the system! Please try again with a different module name."))
                isTrue = true;
        }
        assertTrue(isTrue);
        System.out.println("[TEST] Fail to add a module with an existing module name [PASSED]");
    }

    @Test
    @Order(3)
    public void getAllModules() {
        List<ModuleDto> moduleDtoList = moduleService.getAllModules();

        boolean isTrue = moduleDtoList.size() > 0;

        assertTrue(isTrue);
        System.out.println("[TEST] Get all modules [PASSED]");
    }

//    @Test
//    @Order(4)
//    public void getModuleByModuleId() {
//        ModuleDto moduleDto = moduleService.findModuleByModuleId(moduleId);
//
//        boolean isTrue = moduleDto != null;
//
//        assertTrue(isTrue);
//        System.out.println("[TEST] Get module by module Id [PASSED]");
//    }
//
//    @Test
//    @Order(5)
//    public void deleteModuleByModuleId() throws Exception {
//        moduleService.deleteModuleByModuleId(moduleId);
//
//        List<ModuleDto> moduleDtoList = moduleService.getAllModules();
//
//        boolean isTrue = true;
//
//        for (ModuleDto moduleDto : moduleDtoList) {
//            if (moduleDto.getModuleId().equals(moduleId)) {
//                isTrue = false;
//                break;
//            }
//        }
//
//        assertTrue(isTrue);
//        System.out.println("[TEST] Delete module by module id [PASSED]");
//    }
}
