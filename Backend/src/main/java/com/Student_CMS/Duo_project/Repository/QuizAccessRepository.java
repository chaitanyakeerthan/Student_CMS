package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.QuizAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAccessRepository
        extends JpaRepository<QuizAccess, Long> {

    boolean existsByQuiz_IdAndStudent_Id(Long quizId, Long studentId);
}

