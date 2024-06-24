package ro.sapientia.eysenck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.eysenck.models.ResponseText;
import ro.sapientia.eysenck.models.Results;
import ro.sapientia.eysenck.repository.ResultsRepository;
import ro.sapientia.eysenck.services.ResultsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ResultsServiceImpl implements ResultsService {

    @Autowired
    private ResultsRepository resultsRepository;

    @Override
    public void evaluateResponses(Map<String, Integer> categoryMap, Long questionId, ResponseText text) {

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
    public void saveResult(Long userId, int extroversion, int honesty, int neuroticism, int rigidity, LocalDate date) {
        List<Results> resultsList = resultsRepository.findByUserIdAndCreatedAt(userId, date);

        if (resultsList.isEmpty()) {
            Results results = new Results();
            results.setUserId(userId);
            results.setExtroversion(extroversion);
            results.setNeuroticism(neuroticism);
            results.setRigidity(rigidity);
            results.setHonesty(honesty);
            results.setCreatedAt(date);

            resultsRepository.save(results);
        }
    }

    @Override
    public Map<String, List<Integer>> getResultMap(Long userId) {
        List<Results> resultsList = resultsRepository.findByUserId(userId);

        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (Results result : resultsList) {
            String date = result.getCreatedAt().toString();
            List<Integer> values = new ArrayList<>();

            values.add(result.getExtroversion());
            values.add((result.getHonesty()));
            values.add((result.getNeuroticism()));
            values.add((result.getRigidity()));

            resultMap.put(date, values);
        }
        return resultMap;
    }
}