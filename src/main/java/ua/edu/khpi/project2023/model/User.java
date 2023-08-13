package ua.edu.khpi.project2023.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table( name = "user",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    @Size(max = 45)
    private String name;

    @NotBlank
    @NonNull
    @Size(max = 45)
    @Email
    private String email;

    @NotBlank
    @NonNull
    @Size(max = 120)
    private String password;
    private String subject;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    public User() {
    }

    public User(Long id, @NonNull String name, @NonNull String email, @NonNull String password, String subject) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subject = subject;
    }
}
