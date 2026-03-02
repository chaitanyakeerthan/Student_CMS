package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.StudentCreateRequest;
import com.Student_CMS.Duo_project.Entity.Student;

public interface StudentService {

    Student createStudent(StudentCreateRequest request);
    Student login(String email, String password);
}
