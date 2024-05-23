package ro.sapientia.eysenck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.eysenck.models.Question;
import ro.sapientia.eysenck.repository.QuestionRepository;
import ro.sapientia.eysenck.services.QuestionService;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElseThrow(() -> new RuntimeException("Question with id " + id + " not found"));
    }
}
