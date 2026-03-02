package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Entity.Quiz;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface QuizService {
    Quiz createQuiz(Quiz quiz);
    List<Quiz> getQuizzesByBranch(String branch);
    void deleteQuiz(Long id);
}
