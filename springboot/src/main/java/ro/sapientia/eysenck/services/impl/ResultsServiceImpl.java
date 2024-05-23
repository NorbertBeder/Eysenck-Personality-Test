package ro.sapientia.eysenck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.eysenck.models.Results;
import ro.sapientia.eysenck.repository.ResultsRepository;
import ro.sapientia.eysenck.services.ResultsService;
import java.util.List;

import java.time.LocalDate;

@Service
public class ResultsServiceImpl implements ResultsService {

    @Autowired
    private ResultsRepository resultsRepository;
    @Override
    public void saveResult(Long userId, int extroversion, int honesty, int neuroticism, int rigidity, LocalDate date) {
        List<Results> resultsList =  resultsRepository.findByUserIdAndCreatedAt(userId, date);

        if(resultsList.isEmpty()){
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
}
