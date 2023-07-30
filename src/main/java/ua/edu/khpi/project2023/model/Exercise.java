package ua.edu.khpi.project2023.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;

    private String question;

    private String answer;

    public Exercise() {
    }
}
