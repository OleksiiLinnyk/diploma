package ua.edu.khpi.project2023.exercise.inspector;

import ua.edu.khpi.project2023.exception.UnsupportedExerciseInspection;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommandContainer;
import ua.edu.khpi.project2023.exercise.model.IExercise;

public final class ExerciseInspector {

    private static InspectorCommandContainer commandContainer = new InspectorCommandContainer();

    public static int inspectExercise(IExercise IExercise, String rightAnswer) throws UnsupportedExerciseInspection {
        InspectorCommand command = commandContainer.getCommand(IExercise.getType());
        return command.inspectExercise(IExercise, rightAnswer);
    }
}
