package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}
