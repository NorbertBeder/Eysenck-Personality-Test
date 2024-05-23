package ro.sapientia.eysenck.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.eysenck.models.Results;
import ro.sapientia.eysenck.repository.ResultsRepository;
import ro.sapientia.eysenck.services.UserService;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("https://dnx1w3861mokj.cloudfront.net")
public class UserController {
    private final UserService userService;

    private final ResultsRepository resultsRepository;

    public UserController(UserService userService, ResultsRepository resultsRepository) {
        this.userService = userService;
        this.resultsRepository = resultsRepository;
    }

    @GetMapping("/checkUser")
    public Long checkEmail(@RequestParam String email) {
        return userService.getUserIdByEmail(email);
    }

    @GetMapping("/dates")
    public Map<String, List<Integer>> getResponseDate(@RequestParam("email") String email) {
        Long userId = userService.getUserIdByEmail(email);

        List<Results> resultsList = resultsRepository.findByUserId(userId);

        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (Results result : resultsList) {
            String date = result.getCreatedAt().toString();
            List<Integer> values = new ArrayList<>();

            values.add(result.getExtroversion());
            values.add((result.getHonesty()));
            values.add((result.getNeuroticism()));
            values.add((result.getRigidity()));

            resultMap.put(date, values);
        }
        return resultMap;
    }
}
