package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Department;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    Optional<Department> findByDeptName(DepartmentType deptName);
}
