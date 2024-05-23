package ro.sapientia.eysenck.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.eysenck.models.ResponseText;
import ro.sapientia.eysenck.models.User;
import ro.sapientia.eysenck.services.ResponseService;
import ro.sapientia.eysenck.services.ResultsService;
import ro.sapientia.eysenck.services.UserService;
import ro.sapientia.eysenck.models.ResultDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("https://dnx1w3861mokj.cloudfront.net")
public class ResponseController {
    private final ResponseService responseService;
    private final UserService userService;

    private final ResultsService resultsService;

    public ResponseController(ResponseService responseService, UserService userService, ResultsService resultsService) {
        this.responseService = responseService;
        this.userService = userService;
        this.resultsService = resultsService;
    }


    @PostMapping("/result")
    public Map<String, Integer> getResult(@RequestBody ResultDTO resultDTO){
        User tempUser = resultDTO.getUser();
        Map<Long, String> answers = resultDTO.getAnswers();

        Long id = userService.getUserIdByEmail(tempUser.getEmail());


        if (id == null) {
            User user = userService.createUser(tempUser.getBirthYear(), tempUser.getGender(), tempUser.getEmail());
            id = user.getId();
        }
        Map<String, Integer> categoryMap = new LinkedHashMap<>();
        categoryMap.put("extrovertizmus", 0);
        categoryMap.put("őszinteség", 0);
        categoryMap.put("neuroticizmus", 0);
        categoryMap.put("ridegség", 0);

        LocalDate date = LocalDate.now();

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String answer = entry.getValue();
            ResponseText responseText = (answer.equals("YES") ? ResponseText.YES : ResponseText.NO);


            responseService.saveResponse(id, questionId, responseText, date);
            responseService.evaluateResponse(categoryMap, questionId, responseText);
        }
        resultsService.saveResult(id,
                categoryMap.get("extrovertizmus") != null ? categoryMap.get("extrovertizmus") : 0,
                categoryMap.get("őszinteség") != null ? categoryMap.get("őszinteség") : 0,
                categoryMap.get("neuroticizmus") != null ? categoryMap.get("neuroticizmus") : 0,
                categoryMap.get("ridegség") != null ? categoryMap.get("ridegség") : 0,
                date
        );
        return categoryMap;
    }
}
