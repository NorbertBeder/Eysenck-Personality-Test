package ro.sapientia.eysenck.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "responses")
@Data
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    @Enumerated(EnumType.STRING)
    private ResponseText text;

    private long userId;

    private long questionId;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
