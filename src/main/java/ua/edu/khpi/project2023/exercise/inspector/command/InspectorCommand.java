package ua.edu.khpi.project2023.exercise.inspector.command;

import ua.edu.khpi.project2023.exercise.model.IExercise;

public interface InspectorCommand {

    int inspectExercise(IExercise IExercise, String rightAnswer);
}
