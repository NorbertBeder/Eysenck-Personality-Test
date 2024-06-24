package ro.sapientia.eysenck.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.eysenck.models.Results;
import ro.sapientia.eysenck.models.User;
import ro.sapientia.eysenck.repository.ResultsRepository;
import ro.sapientia.eysenck.services.UserService;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/checkUser")
    public Optional<User> getUserEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return userService.getUserByEmail(email);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String runTimeExceptionHandler(RuntimeException e){
        return e.getMessage();
    }
}
