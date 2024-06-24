package ro.sapientia.eysenck.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sapientia.eysenck.models.UserInfoDTO;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @GetMapping("/userInfo")
    public UserInfoDTO login(Authentication authentication) {
        if (authentication == null){
            return null;
        }
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();
        String name = attributes.getOrDefault("name", "").toString();
        String profilePictureUrl = attributes.getOrDefault("picture", "").toString();
        return new UserInfoDTO(email, profilePictureUrl, name);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}