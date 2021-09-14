package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mailHandler.EmailService;
import com.example.findmylecture.model.User;
import com.example.findmylecture.repository.ModuleRepo;
import com.example.findmylecture.repository.RoleRepo;
import com.example.findmylecture.repository.UserRepo;
import com.example.findmylecture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;

    @Override
    public void save(UserDto userDto) throws Exception {
        if (userRepo.findByEmail(userDto.getEmail()).size() != 0) {
            throw new Exception("A user with the entered email already exists in the system! Please try again with a different email.");
        } else if (userRepo.findByNic(userDto.getNic()).size() != 0) {
            throw new Exception("The user with the entered NIC is already registered in the system!");
        } else {
            User user = new User();
            User tempUser = new User();

            //if no users exists in the database;
            long userCount = userRepo.count();

            if (userCount == 0) {
                long usernameNumber = userRepo.count() + 1;
                long tempUsername = userRepo.count() + 2;
                String usernamePassword = "SU0" + usernameNumber;

                user.setUsername(usernamePassword);
                user.setFirstname(userDto.getFirstname());
                user.setLastname(userDto.getLastname());
                user.setEmail(userDto.getEmail());
                user.setNic(userDto.getNic());
                user.setContactNumber(userDto.getContactNumber());
                user.setPassword(bCryptPasswordEncoder.encode(usernamePassword));
                user.setRole(userDto.getRole());

                userRepo.save(user);

                tempUser.setUsername("temp");
                tempUser.setNic(Long.toString(tempUsername));

                userRepo.save(tempUser);

                emailService.staffRegister(user);

            } else {
                tempUser = userRepo.findUserByUsername("temp");
                String usernamePassword = "SU0" + tempUser.getNic();
                long tempNic = Long.parseLong(tempUser.getNic()) + 1;

                userRepo.deleteById("temp");
                User newTempUser = new User();

                user.setUsername(usernamePassword);
                user.setFirstname(userDto.getFirstname());
                user.setLastname(userDto.getLastname());
                user.setEmail(userDto.getEmail());
                user.setNic(userDto.getNic());
                user.setContactNumber(userDto.getContactNumber());
                user.setPassword(bCryptPasswordEncoder.encode(usernamePassword));
                user.setRole(userDto.getRole());

                userRepo.save(user);

                newTempUser.setUsername("temp");
                if (tempNic < 10)
                    newTempUser.setNic("0" + tempNic);
                else
                    newTempUser.setNic(Long.toString(tempNic));

                userRepo.save(newTempUser);

                emailService.staffRegister(user);
            }
        }
    }

    @Override
    public void saveStudent(UserDto userDto) throws Exception {
        if (userRepo.findByEmail(userDto.getEmail()).size() != 0) {
            throw new Exception("A user with the entered email already exists in the system! Please try again with a different email.");
        } else if (userRepo.findByNic(userDto.getNic()).size() != 0) {
            throw new Exception("The user with the entered NIC is already registered in the system!");
        } else {


            User user = new User();
            User tempUser = new User();
            String studentRole = "STUDENT";

            //if no users exists in the database;
            long userCount = userRepo.count();

            if (userCount == 0) {
                long usernameNumber = userRepo.count() + 1;
                long tempUsername = userRepo.count() + 2;
                String usernamePassword = "CB0" + usernameNumber;

                user.setUsername(usernamePassword);
                user.setFirstname(userDto.getFirstname());
                user.setLastname(userDto.getLastname());
                user.setEmail(userDto.getEmail());
                user.setNic(userDto.getNic());
                user.setContactNumber(userDto.getContactNumber());
                user.setPassword(bCryptPasswordEncoder.encode(usernamePassword));
                user.setRole(roleRepo.findRoleByRoleName(studentRole));
                if (userDto.getBatch() != null) {
                    if (!userDto.getBatch().getBatchCode().equals("")) {
                        if (userDto.getBatch().getIntakeDate().before(Date.valueOf(LocalDate.now().plusMonths(3)))) {
                            user.setBatch(userDto.getBatch());
                        } else {
                            throw new Exception("The batch selected for the student conducted lectures over three months! Please try again with another batch.");
                        }
                    }
                }
                userRepo.save(user);
                emailService.studentRegister(user);

                tempUser.setUsername("temp");
                tempUser.setNic(Long.toString(tempUsername));

                userRepo.save(tempUser);
            } else {
                tempUser = userRepo.findUserByUsername("temp");
                String usernamePassword = "CB" + tempUser.getNic();
                long tempNic = Long.parseLong(tempUser.getNic()) + 1;

                userRepo.deleteById("temp");
                User newTempUser = new User();

                user.setUsername(usernamePassword);
                user.setFirstname(userDto.getFirstname());
                user.setLastname(userDto.getLastname());
                user.setEmail(userDto.getEmail());
                user.setNic(userDto.getNic());
                user.setContactNumber(userDto.getContactNumber());
                user.setPassword(bCryptPasswordEncoder.encode(usernamePassword));
                user.setRole(roleRepo.findRoleByRoleName(studentRole));
                if (userDto.getBatch() != null) {
                    if (!userDto.getBatch().getBatchCode().equals("")) {
                        if (userDto.getBatch().getIntakeDate().before(Date.valueOf(LocalDate.now().plusMonths(3)))) {
                            user.setBatch(userDto.getBatch());
                        } else {
                            throw new Exception("The batch selected for the student conducted lectures over three months! Please try again with another batch.");
                        }
                    }
                }
                userRepo.save(user);
                emailService.studentRegister(user);

                newTempUser.setUsername("temp");
                if (tempNic < 10)
                    newTempUser.setNic("0" + tempNic);
                else
                    newTempUser.setNic(Long.toString(tempNic));

                userRepo.save(newTempUser);
            }
        }
    }


    @Override
    public UserDto findByUsername(String username) {
        UserDto userDto = new UserDto();
        User user = userRepo.findUserByUsername(username);

        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setContactNumber(user.getContactNumber());
        userDto.setEmail(user.getEmail());
        userDto.setBatch(user.getBatch());
        userDto.setModules(user.getModules());

        return userDto;
    }

    @Override
    public UserDto updatable(String username) {
        UserDto userDto = new UserDto();
        User user = userRepo.findUserByUsername(username);

        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setContactNumber(user.getContactNumber());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(null);

        return userDto;
    }

    @Override
    public Long getUserRole(String name) {
        User user = userRepo.findUserByUsername(name);
        return user.getRole().getRoleId();
    }

    @Override
    public String findByEmail(String email) {
        User user = userRepo.findUserByEmail(email);
        return user.getUsername();
    }

    @Override
    public List<UserDto> findAllLecturers() {
        List<UserDto> lecturerList = new ArrayList<>();

        for (User user : userRepo.findAllLecturers()) {
            UserDto userDto = new UserDto();

            userDto.setUsername(user.getUsername());
            userDto.setFirstname(user.getFirstname());
            userDto.setLastname(user.getLastname());
            userDto.setContactNumber(user.getContactNumber());
            userDto.setEmail(user.getEmail());
            userDto.setModules(user.getModules());

            lecturerList.add(userDto);
        }
        return lecturerList;
    }

    @Override
    public List<UserDto> findAllStudents() {
        List<UserDto> studentList = new ArrayList<>();

        for (User user : userRepo.findAllStudents()) {
            UserDto userDto = new UserDto();

            userDto.setUsername(user.getUsername());
            userDto.setFirstname(user.getFirstname());
            userDto.setLastname(user.getLastname());
            userDto.setContactNumber(user.getContactNumber());
            userDto.setEmail(user.getEmail());
            userDto.setBatch(user.getBatch());

            studentList.add(userDto);
        }
        return studentList;
    }

    @Override
    public void updateStudent(UserDto userDto, String username) throws Exception {
        List<User> usersWithEmail = userRepo.findByEmail(userDto.getEmail());
        if (usersWithEmail.size() != 0) {
            for (User userWithEmail : usersWithEmail) {
                if (!username.equals(userWithEmail.getUsername())) {
                    throw new Exception("Another user with the entered email already exists in the system!\nPlease try again with a different email.");
                }
            }
        }

        User student = new User();

        Optional<User> thisUser = Optional.ofNullable(userRepo.findUserByUsername(username));
        if (thisUser.isPresent()) {
            student = thisUser.get();
        }

        student.setFirstname(userDto.getFirstname());
        student.setLastname(userDto.getLastname());
        student.setEmail(userDto.getEmail());
        student.setContactNumber(userDto.getContactNumber());

        userRepo.save(student);
    }

    @Override
    public void updateLecturer(String username, UserDto userDto) throws Exception {
        List<User> usersWithEmail = userRepo.findByEmail(userDto.getEmail());
        if (usersWithEmail.size() != 0) {
            for (User userWithEmail : usersWithEmail) {
                if (!username.equals(userWithEmail.getUsername())) {
                    throw new Exception("Another user with the entered email already exists in the system!\nPlease try again with a different email.");
                }
            }
        }

        User lecturer = new User();

        Optional<User> thisUser = Optional.ofNullable(userRepo.findUserByUsername(username));
        if (thisUser.isPresent()) {
            lecturer = thisUser.get();
        }

        lecturer.setFirstname(userDto.getFirstname());
        lecturer.setLastname(userDto.getLastname());
        lecturer.setEmail(userDto.getEmail());
        lecturer.setContactNumber(userDto.getContactNumber());

        userRepo.save(lecturer);
    }

    @Override
    public UserDto findStudentsBatch(String username) {
        UserDto student = new UserDto();

        User user = userRepo.findUserByUsername(username);

        student.setUsername(user.getUsername());
        student.setBatch(user.getBatch());

        return student;
    }

    @Override
    public void updateStudentBatch(String username, UserDto userDto) throws Exception {
        try {
            User student = new User();

            Optional<User> thisUser = Optional.ofNullable(userRepo.findUserByUsername(username));
            if (thisUser.isPresent()) {
                student = thisUser.get();
            }

            if (userDto.getBatch() != null) {
                if (!userDto.getBatch().getBatchCode().equals("")) {
                    if (userDto.getBatch() != student.getBatch()) {
                        student.setBatch(userDto.getBatch());
                        userRepo.save(student);
                        emailService.updateStudentBatch(student);
                    }
                }
            }
        } catch (Exception exception) {
            throw new Exception("An error occurred while assigning the student to the batch!");
        }
    }

    @Override
    public void deAssignStudentBatch(String username) {
        User student = new User();

        Optional<User> thisUser = Optional.ofNullable(userRepo.findUserByUsername(username));
        if (thisUser.isPresent()) {
            student = thisUser.get();
        }

        String currentBatch = student.getBatch().getBatchCode();
        String studentEmail = student.getEmail();

        student.setBatch(null);

        userRepo.save(student);
        emailService.deAssignBatch(currentBatch, studentEmail);
    }

    @Override
    public void updateProfile(UserDto userDto, String username) throws Exception {
        User user = new User();

        Optional<User> thisUser = Optional.ofNullable(userRepo.findUserByUsername(username));
        if (thisUser.isPresent()) {
            user = thisUser.get();
        }

        List<User> usersWithEmail = userRepo.findByEmail(userDto.getEmail());
        if (usersWithEmail.size() != 0) {
            for (User userWithEmail : usersWithEmail) {
                if (!user.getUsername().equals(userWithEmail.getUsername())) {
                    throw new Exception("A user with the entered email already exists in the system!\n" +
                            "Please try again with a different email.");
                }
            }
        }

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        if (!userDto.getPassword().equals("") && userDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }

        userRepo.save(user);
    }

    @Override
    public void deleteUserByUsername(String username) throws Exception {
        try {
            UserDto user = findByUsername(username);
            userRepo.deleteById(username);
            emailService.deleteUser(user);
        } catch (Exception exception) {
            throw new Exception("An error occurred while deleting the user, Please try again later!");
        }
    }

    @Override
    public void removeBatchFromStudents(Long batchId) throws Exception {
        List<User> users = userRepo.findByBatch(batchId);
        if (users.size() != 0) {
            throw new Exception("There are students assigned to this batch! " +
                    "Please de-assign or update the students batches before removing the batch");
        }
    }

}
