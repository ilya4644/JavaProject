package entities;

import java.util.UUID;

public class Task {
    private final UUID ulearnId;
    private final TaskType taskType;
    private final int maxScore;
    private int studentScore;
    private final String taskName;

    public Task(UUID ulearnId, TaskType taskType, int maxScore, int studentScore, String taskName) {
        this.ulearnId = ulearnId;
        this.taskType = taskType;
        this.maxScore = maxScore;
        this.studentScore = studentScore;
        this.taskName = taskName;
    }

    public UUID getUlearnId() {
        return this.ulearnId;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public int getStudentScore() {
        return this.studentScore;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    public String getTaskName() {
        return this.taskName;
    }
}
