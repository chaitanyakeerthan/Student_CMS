package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    long countByDepartment_Id(Long departmentId);

    List<Student> findByDepartment_Id(Long id);
}
