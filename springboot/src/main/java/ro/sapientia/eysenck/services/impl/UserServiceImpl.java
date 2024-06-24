package ro.sapientia.eysenck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.eysenck.models.Gender;
import ro.sapientia.eysenck.models.User;
import ro.sapientia.eysenck.repository.UserRepository;
import ro.sapientia.eysenck.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(Integer birthYear, Gender gender, String email) {
        Optional<User> existingUserOptional = userRepository.findByEmail(email);
        if (existingUserOptional.isPresent()) {
            return existingUserOptional.get();
        }

        User user = new User();
        user.setBirthYear(birthYear);
        user.setGender(gender);
        user.setEmail(email);

        return userRepository.save(user);
    }

    public Long getUserIdByEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.map(User::getId).orElse(null);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
