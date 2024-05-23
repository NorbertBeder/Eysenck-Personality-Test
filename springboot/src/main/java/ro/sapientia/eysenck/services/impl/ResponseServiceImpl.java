package ro.sapientia.eysenck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.eysenck.models.Response;
import ro.sapientia.eysenck.models.ResponseText;
import ro.sapientia.eysenck.repository.ResponseRepository;
import ro.sapientia.eysenck.services.ResponseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;


    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public void evaluateResponse(Map<String, Integer> categoryMap, Long questionId, ResponseText text) {

        switch(text) {
            case YES:
                switch (questionId.intValue()) {
                    case 1, 6, 8, 12, 17, 20, 24, 36, 44, 48:
                        categoryMap.merge("extrovertizmus", 2, Integer::sum);
                        break;
                    case 2, 5, 10, 13, 16, 18, 22, 25, 27, 30, 33, 35, 40, 42, 45, 47, 52, 55, 57:
                        categoryMap.merge("neuroticizmus", 2, Integer::sum);
                        break;
                    case 3, 7, 11, 14, 19, 21, 26, 29, 34, 39, 41, 46, 51, 56:
                        categoryMap.merge("ridegség", 2, Integer::sum);
                        break;
                    case 4, 9, 15, 32, 37:
                        categoryMap.merge("őszinteség", 2, Integer::sum);
                        break;
                }
                break;
            case NO:
                switch (questionId.intValue()) {
                    case 28, 31, 38, 49, 54, 58:
                        categoryMap.merge("extrovertizmus", 2, Integer::sum);
                        break;
                    case 53:
                        categoryMap.merge("ridegség", 2, Integer::sum);
                        break;
                    case 23, 43, 50:
                        categoryMap.merge("őszinteség", 2, Integer::sum);
                        break;
                }
                break;
        }
    }

    @Override
    public void saveResponse(Long userId, Long questionId, ResponseText response, LocalDate date) {
        List<Response> responses = responseRepository.findByUserIdAndQuestionIdAndCreatedAt(userId, questionId, date);

        if (responses.isEmpty()) {
            Response userResponse = new Response();
            userResponse.setUserId(userId);
            userResponse.setQuestionId(questionId);
            userResponse.setText(response);
            userResponse.setCreatedAt(date);
            responseRepository.save(userResponse);
        }
    }
}
