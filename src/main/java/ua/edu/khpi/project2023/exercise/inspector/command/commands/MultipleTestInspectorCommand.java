package ua.edu.khpi.project2023.exercise.inspector.command.commands;

import org.apache.commons.lang3.StringUtils;
import ua.edu.khpi.project2023.exercise.inspector.command.InspectorCommand;
import ua.edu.khpi.project2023.exercise.model.IExercise;
import ua.edu.khpi.project2023.exercise.model.TestExercise;

import java.util.ArrayList;
import java.util.List;

public class MultipleTestInspectorCommand implements InspectorCommand {

    @Override
    public int inspectExercise(IExercise IExercise, String rightAnswer) {
        if (!(IExercise instanceof TestExercise)) {
            throw new IllegalArgumentException();
        }

        List<Integer> rightAnswerIndexes = parseRightAnswer(rightAnswer);
        TestExercise testExercise = (TestExercise) IExercise;
        List<Integer> givenAnswerIndexes = testExercise.getGivenAnswerIndexes();

        if (rightAnswerIndexes.size() == givenAnswerIndexes.size()
                && givenAnswerIndexes.containsAll(rightAnswerIndexes)) {

            return testExercise.getPoints();
        }
        return 0;
    }

    private List<Integer> parseRightAnswer(String rightAnswer) {
        String[] indexesStrings = rightAnswer.split(",");
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < indexesStrings.length; i++) {
            String indexString = indexesStrings[i];
            if (!StringUtils.isNumeric(indexString)) {
                throw new IllegalArgumentException();
            }
            indexes.add(Integer.parseInt(indexString));
        }
        return indexes;
    }
}
