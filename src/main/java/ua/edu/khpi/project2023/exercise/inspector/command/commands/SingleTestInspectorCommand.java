package ua.edu.khpi.project2023.exercise.inspector.command.commands;

import org.apache.commons.lang3.StringUtils;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.model.Exercise;
import ua.edu.khpi.project2023.exercise.model.TestExercise;

public class SingleTestInspectorCommand implements InspectorCommand {

    @Override
    public int inspectExercise(Exercise exercise, String rightAnswer) {
        if (!(exercise instanceof TestExercise) || !StringUtils.isNumeric(rightAnswer)) {
            throw new IllegalArgumentException();
        }
        TestExercise testExercise = (TestExercise) exercise;
        int rightAnswerIndex = Integer.parseInt(rightAnswer);

        if (testExercise.getGivenAnswerIndexes().size() == 1
                && testExercise.getGivenAnswerIndexes().get(0).equals(rightAnswerIndex)) {

            return testExercise.getPoints();
        }
        return 0;
    }
}
