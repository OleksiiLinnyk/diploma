package ua.edu.khpi.project2023.exercise.inspector.command.commands;

import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.model.IExercise;
import ua.edu.khpi.project2023.exercise.model.OpenExercise;

public class ShortAnswerInspectorCommand implements InspectorCommand {

    @Override
    public int inspectExercise(IExercise IExercise, String rightAnswer) {
        if (!(IExercise instanceof OpenExercise)) {
            throw new IllegalArgumentException();
        }
        OpenExercise openExercise = (OpenExercise) IExercise;

        if (openExercise.getGivenAnswer().toLowerCase().equals(rightAnswer.toLowerCase())) {
            return openExercise.getPoints();
        }
        return 0;
    }
}
