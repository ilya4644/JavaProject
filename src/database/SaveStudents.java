package database;

import entities.Section;
import entities.Student;
import entities.StudentGrade;
import entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class SaveStudents {
    private final ArrayList<Student> students;
    private final String databaseUrl;
    private final String user;
    private final String password;


    public SaveStudents(ArrayList<Student> students, String databaseUrl, String user, String password) {
        this.students = students;
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public void saveToDB() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        for (Student student: students) {
            saveStudents(connection, student);
        }
        connection.close();
    }

    private void saveStudents(Connection connection, Student student) throws SQLException {
        String studentQuery = "INSERT INTO students VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(studentQuery);
        statement.setObject(1, student.getUlearnId());
        statement.setString(2, student.getSurname());
        statement.setString(3, student.getName());
        statement.setString(4, student.getEmail());
        statement.setString(5, student.getGroup());
        statement.execute();
        saveStudentGrade(connection, student.getStudentGrade());
    }

    private void saveStudentGrade(Connection connection, StudentGrade studentGrade) throws SQLException {
        String studentGradeQuery = "INSERT INTO student_grade VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(studentGradeQuery);
        statement.setObject(1, studentGrade.getUlearnId());
        statement.setInt(2, studentGrade.getMaxActivitiesScore());
        statement.setInt(3, studentGrade.getMaxExerciseScore());
        statement.setInt(4, studentGrade.getMaxPracticeScore());
        statement.setInt(5, studentGrade.getMaxSeminarScore());
        statement.setInt(6, studentGrade.getTotalActivitiesScore());
        statement.setInt(7, studentGrade.getTotalExerciseScore());
        statement.setInt(8, studentGrade.getTotalPracticeScore());
        statement.setInt(9, studentGrade.getTotalSeminarScore());
        statement.execute();
        saveStudentSections(connection, studentGrade.getSections());
    }

    private void saveStudentSections(Connection connection, ArrayList<Section> sections) throws SQLException {
        String sectionQuery = "INSERT INTO sections VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sectionQuery);
        for (Section section: sections) {
            statement.setObject(1, section.getUlearnId());
            statement.setObject(2, section.getSectionId());
            statement.setString(3, section.getSectionName());
            statement.setInt(4, section.getMaxExerciseScore());
            statement.setInt(5, section.getMaxPracticeScore());
            statement.setInt(6, section.getMaxSeminarScore());
            statement.addBatch();
            saveStudentTask(connection, section.getTasks(), section.getSectionId());
        }
        statement.executeBatch();
    }

    private void saveStudentTask(Connection connection, ArrayList<Task> tasks, UUID sectionId) throws SQLException {
        String taskQuery = "INSERT INTO tasks VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(taskQuery);
        for (Task task: tasks) {
            statement.setObject(1, task.getUlearnId());
            statement.setObject(2, task.getTaskId());
            statement.setString(3, String.valueOf(task.getTaskType()));
            statement.setInt(4, task.getMaxScore());
            statement.setInt(5, task.getStudentScore());
            statement.setString(6, task.getTaskName());
            statement.setObject(7, sectionId);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
