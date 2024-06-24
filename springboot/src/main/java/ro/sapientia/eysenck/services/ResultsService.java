package ro.sapientia.eysenck.services;

import ro.sapientia.eysenck.models.ResponseText;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ResultsService {

    void evaluateResponses(Map<String, Integer> categoryMap, Long questionId, ResponseText text);
    void saveResult(Long userId, int extroversion, int honesty, int neuroticism, int rigidity, LocalDate date);
    Map<String, List<Integer>>  getResultMap(Long userId);
}