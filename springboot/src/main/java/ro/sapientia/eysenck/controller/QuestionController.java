package ro.sapientia.eysenck.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.eysenck.models.Question;
import ro.sapientia.eysenck.services.QuestionService;
import ro.sapientia.eysenck.services.impl.SecretsManagerServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("https://dnx1w3861mokj.cloudfront.net")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @RequestMapping("/questions/{questionId}")
    public Question getQuestion(@PathVariable("questionId") Long questionId){
        return questionService.getQuestionById(questionId);
    }

    @Autowired
    private SecretsManagerServiceImpl secretsManagerService;

    @GetMapping("/secret/{secretName}")
    public String getSecret(@PathVariable String secretName) {
        return secretsManagerService.getSecret(secretName);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String runTimeExceptionHandler(RuntimeException e){
        return e.getMessage();
    }
}
