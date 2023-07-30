package ua.edu.khpi.project2023.exercise.inspector.command.commands;

import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.model.Exercise;
import ua.edu.khpi.project2023.exercise.model.OpenExercise;

public class ShortAnswerInspectorCommand implements InspectorCommand {

    @Override
    public int inspectExercise(Exercise exercise, String rightAnswer) {
        if (!(exercise instanceof OpenExercise)) {
            throw new IllegalArgumentException();
        }
        OpenExercise openExercise = (OpenExercise) exercise;

        if (openExercise.getGivenAnswer().toLowerCase().equals(rightAnswer.toLowerCase())) {
            return openExercise.getPoints();
        }
        return 0;
    }
}
