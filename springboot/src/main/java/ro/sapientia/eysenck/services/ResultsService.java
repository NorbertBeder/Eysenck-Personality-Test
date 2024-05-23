package ro.sapientia.eysenck.services;

import java.time.LocalDate;

public interface ResultsService {

    void saveResult(Long userId, int extroversion, int honesty, int neuroticism, int rigidity, LocalDate date);
}