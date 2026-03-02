package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Assignment;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByFaculty_Id(Long facultyId);

    List<Assignment> findByDepartment_DeptName(DepartmentType deptName);
}

