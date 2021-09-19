package com.example.findmylecture.repository;

import com.example.findmylecture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findUserByUsername(String username);

    @Query("from User u where u.role.roleName='LECTURER'")
    List<User> findAllLecturers();

    @Query("from User u where u.role.roleName='STUDENT'")
    List<User> findAllStudents();

    @Query("from User u where u.batch.batchId=:batchId")
    List<User> findByBatch(Long batchId);

    @Query("select m.user from Module m where m.moduleId=:moduleId")
    User findEmailOfLecturerByModuleId(Long moduleId);

    @Query("from User u where u.email=:email")
    List<User> findByEmail(String email);

    @Query("from User u where u.nic=:nic")
    List<User> findByNic(String nic);

    @Query("from User u where u.email=:email")
    User findUserByEmail(String email);
}
