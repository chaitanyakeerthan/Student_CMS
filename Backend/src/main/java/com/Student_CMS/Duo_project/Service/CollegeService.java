package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.CollegeResponse;
import com.Student_CMS.Duo_project.Entity.College;

public interface CollegeService {

    College createCollegeIfNotExists();
    CollegeResponse getCollege();
}
