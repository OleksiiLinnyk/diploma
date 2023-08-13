package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestUpsertRequest {

    @NotNull
    private String subject;
    @NotNull
    private String theme;
}
