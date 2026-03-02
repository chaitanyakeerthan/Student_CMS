package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.OpenQuizResponse;
import com.Student_CMS.Duo_project.Entity.Quiz;
import com.Student_CMS.Duo_project.Entity.QuizAccess;
import com.Student_CMS.Duo_project.Entity.Student;
import com.Student_CMS.Duo_project.Repository.QuizAccessRepository;
import com.Student_CMS.Duo_project.Repository.QuizRepository;
import com.Student_CMS.Duo_project.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizAccessServiceImpl implements QuizAccessService {

    private final QuizAccessRepository quizAccessRepository;
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;

    @Override
    public OpenQuizResponse openQuiz(Long quizId, Long studentId) {

        if (quizAccessRepository.existsByQuiz_IdAndStudent_Id(quizId, studentId)) {
            throw new RuntimeException("You have already attempted this quiz");
        }
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        if (quiz.getEndDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Quiz has expired");
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        QuizAccess access = new QuizAccess();
        access.setQuiz(quiz);
        access.setStudent(student);
        access.setAccessed(true);
        access.setAccessedAt(LocalDateTime.now());
        quizAccessRepository.save(access);
        return new OpenQuizResponse(quiz.getQuizLink());
    }
}
