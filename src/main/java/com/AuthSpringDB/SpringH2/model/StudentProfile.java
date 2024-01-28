package com.AuthSpringDB.SpringH2.model;


import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "STUDENTS")
public class StudentProfile {

    @Id
    public Integer studentID;
    public String studentName;
    public String password;

}
