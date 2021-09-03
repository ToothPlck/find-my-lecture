package com.example.findmylecture.repository;

import com.example.findmylecture.model.Batch;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepo extends JpaRepository<Module, Long> {

    @Query("from Module m where m.moduleId=:moduleId")
    Module findModuleByModuleId(Long moduleId);

    @Query("from Module m where m.user.username=:username")
    List<Module> findByUsername(String username);

    @Query("from Module m where m.user.username=:username")
    List<Module> findModulesByUsername(String username);

    @Query("from Module m where m.moduleName=:moduleName")
    List<Module> findModuleByModuleName(String moduleName);

//    @Query("select m.user from Module m where m.moduleId=:moduleId")
//    User findEmailOfLecturerByModuleId(Long moduleId);
}
