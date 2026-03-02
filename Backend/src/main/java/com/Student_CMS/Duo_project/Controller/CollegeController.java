package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.CollegeResponse;
import com.Student_CMS.Duo_project.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    public CollegeResponse getCollege() {

        return collegeService.getCollege();
    }
}
