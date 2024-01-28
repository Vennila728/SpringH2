package com.AuthSpringDB.SpringH2.service;

import com.AuthSpringDB.SpringH2.model.StudentProfile;
import com.AuthSpringDB.SpringH2.repository.StudentRepository;
import com.AuthSpringDB.SpringH2.utils.StudentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Slf4j
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public String signUp(StudentProfile studentProfile) throws Exception {
        if (studentProfile.getStudentID() == null || studentProfile.getPassword() == null || studentProfile.getStudentName() == null)
            throw new Exception(StudentMessage.INVALID_CREDENTIALS);

        if (checkForStudentData(studentProfile.getStudentID())) {
            return StudentMessage.STUDENT_ACCOUNT_EXISTS;
        }
        studentProfile.setPassword(encrypt(studentProfile.getPassword()));
        studentRepository.save(studentProfile);

        return StudentMessage.SIGNUP_SUCCESS;
    }

    public String login(StudentProfile studentProfile) throws Exception {
        if (checkForStudentData(studentProfile.getStudentID())) {
            log.info("LoginValidation {}", checkForStudentData(studentProfile.getStudentID()));
            if (loginValidation(studentProfile))
                return StudentMessage.LOGIN_SUCCESS;
        }

        throw new Exception(StudentMessage.LOGIN_FAILED);
    }

    public StudentProfile getUserInfo(String studentName) {
        return studentRepository.findAll().stream()
                .filter(studentData -> studentData.getStudentName().equalsIgnoreCase(studentName))
                .findFirst()
                .orElse(null);

    }

    public List<StudentProfile> getInfo() {
        return studentRepository.findAll();
    }

    private boolean checkForStudentData(Integer studentId) {
        log.info("checkSForStudentData");
        return studentRepository.findAll().stream().anyMatch(studentData -> studentData.getStudentID().equals(studentId));
    }

    private boolean loginValidation(StudentProfile studentProfile) {
        return studentRepository.findAll().stream().anyMatch(studentData -> studentData.getStudentID().equals(studentProfile.getStudentID()) &&
                studentData.getPassword().equals(encrypt(studentProfile.getPassword())));
    }

    private String encrypt(String password) {
        byte[] encodePassword = Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8));
        return new String(encodePassword);
    }


}
