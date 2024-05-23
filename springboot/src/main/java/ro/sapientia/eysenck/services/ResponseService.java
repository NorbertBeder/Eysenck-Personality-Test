package ro.sapientia.eysenck.services;


import ro.sapientia.eysenck.models.Response;
import ro.sapientia.eysenck.models.ResponseText;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ResponseService {

    List<Response> getAllResponses();

    void evaluateResponse(Map<String, Integer> categoryMap, Long questionId, ResponseText text);

    void saveResponse(Long userId, Long questionId, ResponseText response, LocalDate date);
}
