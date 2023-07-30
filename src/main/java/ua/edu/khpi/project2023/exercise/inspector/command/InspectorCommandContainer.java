package ua.edu.khpi.project2023.exercise.inspector.command;

import ua.edu.khpi.project2023.exception.UnsupportedExerciseInspection;
import ua.edu.khpi.project2023.exercise.ExerciseType;
import ua.edu.khpi.project2023.exercise.inspector.command.commands.MultipleTestInspectorCommand;
import ua.edu.khpi.project2023.exercise.inspector.command.commands.ShortAnswerInspectorCommand;
import ua.edu.khpi.project2023.exercise.inspector.command.commands.SingleTestInspectorCommand;

import java.util.HashMap;
import java.util.Map;

public class InspectorCommandContainer {

    private Map<ExerciseType, InspectorCommand> commands;

    public InspectorCommandContainer() {
        commands = new HashMap<>();
        commands.put(ExerciseType.SINGLE_ANSWER_EXERCISE, new SingleTestInspectorCommand());
        commands.put(ExerciseType.MULTIPLE_ANSWER_EXERCISE, new MultipleTestInspectorCommand());
        commands.put(ExerciseType.SHORT_OPEN_ANSWER_EXERCISE, new ShortAnswerInspectorCommand());
    }

    public InspectorCommand getCommand(ExerciseType type) throws UnsupportedExerciseInspection {
        if (commands.containsKey(type)) {
            return commands.get(type);
        }
        throw new UnsupportedExerciseInspection(
                String.format("Exercise type '%s' doesn't support auto inspection!", type.toString()));
    }
}
