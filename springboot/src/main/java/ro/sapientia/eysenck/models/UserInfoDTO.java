package ro.sapientia.eysenck.models;


import lombok.Data;

@Data
public class UserInfoDTO {
    private String email;
    private String profilePictureUrl;
    private String name;

    public UserInfoDTO(String email, String profilePictureUrl, String name) {
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
    }
}
