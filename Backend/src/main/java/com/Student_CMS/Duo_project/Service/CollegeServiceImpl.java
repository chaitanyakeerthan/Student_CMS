package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.CollegeResponse;
import com.Student_CMS.Duo_project.Entity.College;
import com.Student_CMS.Duo_project.Repository.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    @Override
    @CacheEvict(value = "college", allEntries = true)
    public College createCollegeIfNotExists() {
        return collegeRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    College college = new College();
                    college.setName("ABCollege");
                    return collegeRepository.save(college);
                });
    }

    @Override
    @Cacheable("college")
    public CollegeResponse getCollege() {
        College college = collegeRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("College not initialized"));

        return new CollegeResponse(college.getId(), college.getName());
    }
}
