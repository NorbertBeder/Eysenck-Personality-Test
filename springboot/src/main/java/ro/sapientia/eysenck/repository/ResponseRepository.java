package ro.sapientia.eysenck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.eysenck.models.Response;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByUserId(Long userId);
    List<Response> findByUserIdAndCreatedAt(Long userId, LocalDate date);

    List<Response> findByUserIdAndQuestionIdAndCreatedAt(Long userId, Long questionId, LocalDate date);
}
