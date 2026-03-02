package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Entity.Quiz;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import com.Student_CMS.Duo_project.Repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    @CacheEvict(
            value = "quizzesByBranch",
            allEntries = true
    )
    public Quiz createQuiz(Quiz quiz) {

        return quizRepository.save(quiz);
    }

    @Override
    @Cacheable(
            value = "quizzesByBranch",
            key = "#branch"
    )
    public List<Quiz> getQuizzesByBranch(String branch) {
        return quizRepository.findByDepartment_DeptName(
                DepartmentType.valueOf(branch.toUpperCase())
        );
    }


    @Override
    @CacheEvict(
            value = "quizzesByBranch",
            allEntries = true
    )
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
