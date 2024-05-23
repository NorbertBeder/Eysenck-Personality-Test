package ro.sapientia.eysenck.services;

import ro.sapientia.eysenck.models.Gender;
import ro.sapientia.eysenck.models.Response;
import ro.sapientia.eysenck.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(Integer birthYear, Gender gender, String email);

    List<User> getAllUsers();

    Long getUserIdByEmail(String email);

    List<Response> getResponses(Long userId);

    List<Response> getResponsesByEmailAndDate(String email, LocalDate date);
}

