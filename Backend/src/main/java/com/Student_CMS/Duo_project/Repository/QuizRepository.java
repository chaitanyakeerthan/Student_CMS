package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Quiz;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByDepartment_DeptName(DepartmentType deptName);

    List<Quiz> findByDepartment_DeptNameAndEndDateGreaterThanEqual(
            DepartmentType deptName,
            LocalDate date
    );
}

