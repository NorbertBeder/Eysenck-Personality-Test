package ro.sapientia.eysenck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birthYear")
    @Positive(message = "The birth year needs to be positive")
    @Min(value = 1920, message = "Invalid birth year")
    @Max(value = 2023, message = "Invalid birth year")
    private Integer birthYear;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "email")
    private String email;


}
