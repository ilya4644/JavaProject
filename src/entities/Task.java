package entities;

import java.util.UUID;

public class Task {
    private final UUID ulearnId;
    private final String surname;
    private final String name;
    private final TaskType taskType;
    private final int maxScore;
    private int studentScore;

    public Task(UUID ulearnId, String surname, String name, TaskType taskType, int maxScore, int studentScore) {
        this.ulearnId = ulearnId;
        this.surname = surname;
        this.name = name;
        this.taskType = taskType;
        this.maxScore = maxScore;
        this.studentScore = studentScore;
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

    public int getMaxScore() {
        return maxScore;
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public TaskType getTaskType() {
        return taskType;
    }
}
