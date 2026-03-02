package com.Student_CMS.Duo_project.Config;

import com.Student_CMS.Duo_project.Service.CollegeService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class CollegeDataInit {

    private final CollegeService collegeService;

    @PostConstruct
    public void initCollege() {

        collegeService.createCollegeIfNotExists();
    }
}
