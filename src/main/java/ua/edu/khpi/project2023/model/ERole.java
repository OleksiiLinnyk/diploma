package ua.edu.khpi.project2023.model;

import ua.edu.khpi.project2023.exception.NotFoundException;

public enum ERole {
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN;

    public static ERole findRole(String role) {
        try {
            return valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Role does not exist");
        }
    }
}
