package com.example.findmylecture.mailHandler;

import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.model.Module;
import com.example.findmylecture.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    SimpleMailMessage message = new SimpleMailMessage();

    public void studentRegister(User user) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Welcome to FML!!!");
        if (user.getBatch() != null) {
            message.setText("Thank You " + user.getFirstname() + " for choosing FML for your educational needs!\n" +
                    "You have been assigned to the following batch : " + user.getBatch().getBatchCode() + "\n" +
                    "Your default username and password are set as " + user.getUsername() + ".\n" +
                    "Please login to your account and update your default password for increased security.");
        } else {
            message.setText("Thank You " + user.getFirstname() + " for choosing FML for your educational needs!\n" +
                    "You have not yet been assigned to a batch.\n" +
                    "Your default username and password are set as " + user.getUsername() + ".\n" +
                    "Please login to your account and update your default password for increased security.");
        }

        mailSender.send(message);
    }

    public void staffRegister(User user) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Welcome to FML!!!");
        message.setText("Thank You " + user.getFirstname() + " for joining the FML family!\n" +
                "Your default username and password are set as " + user.getUsername() + ".\n" +
                "Please login to your account and update your default password for increased security.");

        mailSender.send(message);
    }

    public void deAssignBatch(String batchCode, String email) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(email);
        message.setSubject("De-assigned from batch!");
        message.setText("You have been removed from the following batch: " + batchCode + "\n" +
                "You will be assigned to a new batch as soon as possible.\n" +
                "Thank you for your patience.");

        mailSender.send(message);
    }

    public void updateStudentBatch(User user) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Assigned to a new Batch!");
        message.setText("You have been assigned to the following Batch: " + user.getBatch().getBatchCode() + "\n" +
                "Please login to FML to check on upcoming lectures for your batch.");

        mailSender.send(message);
    }

    public void deleteUser(UserDto user) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Successfully removed!");
        message.setText("Thank You " + user.getFirstname() + " for being a part of the FML family!\n" +
                "Your account has been successfully removed from FML.");

        mailSender.send(message);
    }

    public void updateTimeTableStudent(String user, Module module) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user);
        message.setSubject("Successfully removed!");
        message.setText("Dear student, \n" +
                "The previously set schedule for the following module : " + module.getModuleName() + " has been updated.\n" +
                "Please login to FML to view the updated date, time, and class room for the lecture.");

        mailSender.send(message);
    }

    public void updateTimeTableRemoveBatch(String user, Module module) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(user);
        message.setSubject("Successfully removed!");
        message.setText("Dear student, \n" +
                "The previously set schedule for the following module : " + module.getModuleName() + " has been cancelled.");

        mailSender.send(message);
    }

    public void deleteTimetableStudent(String email, String moduleName) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(email);
        message.setSubject("!");
        message.setText("Dear student, \n" +
                "A lecture scheduled for the following module: " + moduleName + " has been cancelled\n" +
                "Please check the timetable for the correct lecture time and dates.");

        mailSender.send(message);
    }

    public void deleteTimetableLecturer(String email, String moduleName) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(email);
        message.setSubject("!");
        message.setText("Dear Lecturer, \n" +
                "A lecture scheduled for the following module: " + moduleName + " has been cancelled\n" +
                "Please check the timetable for the correct lecture time and dates.");

        mailSender.send(message);
    }

    public void removeModuleFromLecturer(String email, String moduleName) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(email);
        message.setSubject("!");
        message.setText("Dear Lecturer, \n" +
                "You have been de-assigned from the following  module: " + moduleName + " due to the removal of the module");

        mailSender.send(message);
    }

    public void deAssignLecturerFromModule(String email, String moduleName) {
        message.setFrom("findmylecture@gmail.com");
        message.setTo(email);
        message.setSubject("!");
        message.setText("Dear Lecturer, \n" +
                "You have been de-assigned from the following  module: " + moduleName);

        mailSender.send(message);
    }
}
