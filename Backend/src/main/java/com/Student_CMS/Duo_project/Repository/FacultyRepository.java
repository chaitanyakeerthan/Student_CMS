package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findByEmail(String email);
}
