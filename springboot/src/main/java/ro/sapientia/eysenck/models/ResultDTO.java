package ro.sapientia.eysenck.models;

import java.util.Map;

import lombok.Data;
import ro.sapientia.eysenck.models.User;

@Data
public class ResultDTO {
    private User user;
    private Map<Long, String> answers;
}
