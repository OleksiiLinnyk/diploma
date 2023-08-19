package ua.edu.khpi.project2023.exercise.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import ua.edu.khpi.project2023.exercise.ExerciseType;
import ua.edu.khpi.project2023.exercise.model.IExercise;
import ua.edu.khpi.project2023.exercise.model.OpenExercise;
import ua.edu.khpi.project2023.exercise.model.TestExercise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseJsonUtil {

    private static Gson gson = new Gson();

    public static String exerciseToJson(IExercise exercise) {
        return gson.toJson(exercise);
    }

    public static IExercise jsonToExercise(String json) {
        ExerciseType type = resolveType(json);
        IExercise exercise;
        if (type.equals(ExerciseType.SINGLE_ANSWER_EXERCISE) || type.equals(ExerciseType.MULTIPLE_ANSWER_EXERCISE)) {
            exercise = gson.fromJson(json, TestExercise.class);
        } else if (type.equals(ExerciseType.SHORT_OPEN_ANSWER_EXERCISE) || type.equals(ExerciseType.LONG_OPEN_ANSWER_EXERCISE)) {
            exercise = gson.fromJson(json, OpenExercise.class);
        } else {
            throw new IllegalArgumentException();
        }
        return exercise;
    }

    public static void writeGivenAnswerToExercise(IExercise exercise, String answer) {
        ExerciseType type = exercise.getType();
        if (type.equals(ExerciseType.SINGLE_ANSWER_EXERCISE) || type.equals(ExerciseType.MULTIPLE_ANSWER_EXERCISE)) {
            List<Integer> answerIndexes = Arrays
                    .stream(answer.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            ((TestExercise) exercise).setGivenAnswerIndexes(answerIndexes);
        } else if (type.equals(ExerciseType.SHORT_OPEN_ANSWER_EXERCISE) || type.equals(ExerciseType.LONG_OPEN_ANSWER_EXERCISE)) {
            ((OpenExercise) exercise).setGivenAnswer(answer);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static ExerciseType resolveType(String json) {
        String type = StringUtils.substringBetween(json, "\"type\":\"", "\"");
        return ExerciseType.valueOf(type);
    }
}
