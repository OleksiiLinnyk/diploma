package ua.edu.khpi.project2023.exercise.inspector.command;

import ua.edu.khpi.project2023.exercise.model.Exercise;

public interface InspectorCommand {

    int inspectExercise(Exercise exercise, String rightAnswer);
}
