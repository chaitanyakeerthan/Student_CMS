package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Course;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByDepartment_Id(int departmentId);
    List<Course> findByDepartment_DeptName(DepartmentType deptName);
}
