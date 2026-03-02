package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.FacultyCreateRequest;
import com.Student_CMS.Duo_project.Entity.Faculty;

public interface FacultyService {

    Faculty createFaculty(FacultyCreateRequest request);
    Faculty login(String email, String password);
}