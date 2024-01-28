package com.AuthSpringDB.SpringH2.controller;

import com.AuthSpringDB.SpringH2.exception.CustomException;
import com.AuthSpringDB.SpringH2.model.StudentProfile;
import com.AuthSpringDB.SpringH2.utils.Routes;
import com.AuthSpringDB.SpringH2.utils.StudentMessage;
import com.AuthSpringDB.SpringH2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Routes.STUDENT)
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping(Routes.SIGNUP)
    public ResponseEntity<?> signUp(@RequestBody StudentProfile studentProfile) throws Exception {
        return ResponseEntity.ok(studentService.signUp(studentProfile));
    }

    @PostMapping(Routes.LOGIN)
    public ResponseEntity<?> login(@RequestBody StudentProfile studentProfile) throws Exception {
        if (studentProfile.getStudentID() == null || studentProfile.getPassword() == null) {
            throw new CustomException(StudentMessage.INVALID_CREDENTIALS);
        }

        return ResponseEntity.ok(studentService.login(studentProfile));
    }


    @GetMapping(Routes.PROFILE)
    public ResponseEntity<?> profile(@PathVariable String username) throws CustomException {
        StudentProfile studentProfile = studentService.getUserInfo(username);
        if (studentProfile == null) {
            throw new CustomException(StudentMessage.INVALID_CREDENTIALS);
        }

        return ResponseEntity.ok(studentProfile);
    }

    @GetMapping(Routes.ALL)
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(studentService.getInfo());
    }
}
