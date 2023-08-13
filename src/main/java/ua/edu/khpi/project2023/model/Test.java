package ua.edu.khpi.project2023.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    private String subject;

    @NotBlank
    @NonNull
    private String theme;

    @NotNull
    @NotBlank
    private boolean enabled;

    @NotNull
    @NotBlank
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User teacher;

    public Test() {
    }

    public Test(@NonNull String subject, @NonNull String theme, User teacher) {
        this.subject = subject;
        this.theme = theme;
        this.enabled = false;
        this.teacher = teacher;
    }
}
