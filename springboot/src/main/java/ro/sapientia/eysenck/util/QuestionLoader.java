package ro.sapientia.eysenck.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ro.sapientia.eysenck.models.Question;
import ro.sapientia.eysenck.repository.QuestionRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionLoader {
    private final QuestionRepository questionRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public QuestionLoader(QuestionRepository questionRepository, ResourceLoader resourceLoader) {
        this.questionRepository = questionRepository;
        this.resourceLoader = resourceLoader;
    }


    public List<String> readQuestionsFromFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        try (InputStream inputStream = resource.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            List<String> questions = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                questions.add(line);
            }
            return questions;
        }
    }
    @PostConstruct
    public void populateQuestionsFromFile() {
        List<String> questionsFromFile;
        try {
            questionsFromFile = readQuestionsFromFile("questions_test.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (questionRepository.count() == 0) {
            for (String questionText : questionsFromFile) {
                Question question = new Question();
                question.setText(questionText);
                questionRepository.save(question);
            }
        }
    }
}
