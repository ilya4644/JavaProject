package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentGrade {
    private final UUID ulearnId;
    private final int maxActivitiesScore;
    private final int maxExerciseScore;
    private final int maxPracticeScore;
    private final int maxSeminarScore;
    private int totalActivitiesScore;
    private int totalExerciseScore;
    private int totalPracticeScore;
    private int totalSeminarScore;
    private final List<Section> sections;

    public StudentGrade(UUID ulearnId, int maxActivitiesScore, int maxExerciseScore, int maxPracticeScore, int maxSeminarScore, int totalActivitiesScore, int totalExerciseScore, int totalPracticeScore, int totalSeminarScore) {
        this.ulearnId = ulearnId;
        this.maxActivitiesScore = maxActivitiesScore;
        this.maxExerciseScore = maxExerciseScore;
        this.maxPracticeScore = maxPracticeScore;
        this.maxSeminarScore = maxSeminarScore;
        this.totalActivitiesScore = totalActivitiesScore;
        this.totalExerciseScore = totalExerciseScore;
        this.totalPracticeScore = totalPracticeScore;
        this.totalSeminarScore = totalSeminarScore;
        sections = new ArrayList<>();
    }

    public UUID getUlearnId() {
        return ulearnId;
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

    public int getTotalExerciseScore() {
        return totalExerciseScore;
    }

    public void setTotalExerciseScore(int totalExerciseScore) {
        this.totalExerciseScore = totalExerciseScore;
    }

    public int getTotalPracticeScore() {
        return totalPracticeScore;
    }

    public void setTotalPracticeScore(int totalPracticeScore) {
        this.totalPracticeScore = totalPracticeScore;
    }

    public int getTotalSeminarScore() {
        return totalSeminarScore;
    }

    public void setTotalSeminarScore(int totalSeminarScore) {
        this.totalSeminarScore = totalSeminarScore;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int getMaxActivitiesScore() {
        return maxActivitiesScore;
    }

    public int getTotalActivitiesScore() {
        return totalActivitiesScore;
    }

    public void setTotalActivitiesScore(int totalActivitiesScore) {
        this.totalActivitiesScore = totalActivitiesScore;
    }
}
