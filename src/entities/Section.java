package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Section {
    private final UUID sectionId;
    private final UUID ulearnId;
    private final String surname;
    private final String name;
    private final int maxExerciseScore;
    private final int maxPracticeScore;
    private final int maxSeminarScore;
    private final List<Task> tasks;

    public Section(UUID sectionId, UUID ulearnId, String surname, String name, int maxExerciseScore, int maxPracticeScore, int maxSeminarScore, List<Task> tasks) {
        this.sectionId = sectionId;
        this.ulearnId = ulearnId;
        this.surname = surname;
        this.name = name;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        this.tasks = new ArrayList<>();
    }

    public UUID getUlearnId() {
        return ulearnId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public int getMaxExerciseScore() {
        return maxExerciseScore;
    }

    public int getMaxPracticeScore() {
        return maxPracticeScore;
    }

    public int getMaxSeminarScore() {
        return maxSeminarScore;
    }

    public UUID getSectionId() {
        return sectionId;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
