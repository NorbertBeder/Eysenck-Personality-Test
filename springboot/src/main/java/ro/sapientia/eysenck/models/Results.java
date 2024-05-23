package ro.sapientia.eysenck.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "results")
@Data
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    @Column(name = "extroversion")
    private int extroversion;

    @Column(name = "neuroticism")
    private int neuroticism;

    @Column(name = "rigidity")
    private int rigidity;

    @Column(name = "honesty")
    private int honesty;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
