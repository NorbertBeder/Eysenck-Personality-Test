package ro.sapientia.eysenck.services;

import ro.sapientia.eysenck.models.Gender;
import ro.sapientia.eysenck.models.Response;
import ro.sapientia.eysenck.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(Integer birthYear, Gender gender, String email);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Long getUserIdByEmail(String email);
}

