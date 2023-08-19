package ua.edu.khpi.project2023.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ua.edu.khpi.project2023.model.ERole;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @Nullable
    private String groupName;
    @Nullable
    private String subject;
    private ERole role;
    @NotBlank
    private String password;
}
