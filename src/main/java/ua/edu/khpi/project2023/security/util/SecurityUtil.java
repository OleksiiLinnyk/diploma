package ua.edu.khpi.project2023.security.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.edu.khpi.project2023.security.model.AuthUser;

@UtilityClass
public final class SecurityUtil {

    public static AuthUser getAuthUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
