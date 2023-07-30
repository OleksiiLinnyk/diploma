package ua.edu.khpi.project2023.exercise.inspector;

import ua.edu.khpi.project2023.exception.UnsupportedExerciseInspection;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommandContainer;
import ua.edu.khpi.project2023.exercise.model.Exercise;

public class ExerciseInspector {

    private InspectorCommandContainer commandContainer = new InspectorCommandContainer();

    public int inspectExercise(Exercise exercise, String rightAnswer) throws UnsupportedExerciseInspection {
        InspectorCommand command = commandContainer.getCommand(exercise.getType());
        return command.inspectExercise(exercise, rightAnswer);
    }

}
