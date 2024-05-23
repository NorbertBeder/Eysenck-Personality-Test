package ro.sapientia.eysenck.services;

import ro.sapientia.eysenck.models.Question;

import java.util.List;

public interface QuestionService{
    List<Question> getAllQuestions();

    Question getQuestionById(Long id);
}
