package com.AuthSpringDB.SpringH2.repository;


import com.AuthSpringDB.SpringH2.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, Integer> {
}
