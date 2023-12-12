package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Section {
    private final String sectionName;
    private final UUID sectionId;
    private final UUID ulearnId;
    private final int maxExerciseScore;
    private final int maxPracticeScore;
    private final int maxSeminarScore;
    private final ArrayList<Task> tasks;

    public Section(UUID sectionId, UUID ulearnId, int maxExerciseScore, int maxPracticeScore, int maxSeminarScore, ArrayList<Task> tasks, String sectionName) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.ulearnId = ulearnId;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        this.tasks = tasks;
    }

    public UUID getUlearnId() {
        return this.ulearnId;
    }

    public int getMaxExerciseScore() {
        return this.maxExerciseScore;
    }

    public int getMaxPracticeScore() {
        return this.maxPracticeScore;
    }

    public int getMaxSeminarScore() {
        return this.maxSeminarScore;
    }

    public UUID getSectionId() {
        return this.sectionId;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public String getSectionName() {
        return this.sectionName;
    }
}
