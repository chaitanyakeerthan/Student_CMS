package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.OpenQuizResponse;

public interface QuizAccessService {
    OpenQuizResponse openQuiz(Long quizId, Long studentId);
}

