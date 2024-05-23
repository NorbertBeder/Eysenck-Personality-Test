package ro.sapientia.eysenck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.eysenck.models.Results;

import java.time.LocalDate;
import java.util.List;

public interface ResultsRepository extends JpaRepository<Results, Long> {
    List<Results> findByUserId(Long userId);

    List<Results> findByUserIdAndCreatedAt(Long userId, LocalDate createdAt);
}
