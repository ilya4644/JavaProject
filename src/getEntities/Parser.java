package getEntities;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class Parser {
    private final String filename;

    int nameIndex = 0;
    int uuidIndex = 1;
    int emailIndex = 2;
    int groupIndex = 3;

    public Parser(String filename) {
        if (checkFile(filename)) {
            this.filename = filename;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean checkFile(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public ArrayList<Student> parseStudents() throws CsvValidationException, IOException {
        CSVReader reader = getReader();

        String[] sectionNamesRow = reader.readNext();
        String[] taskNamesRow = reader.readNext();
        String[] maxScoreRow = reader.readNext();

        ArrayList<Integer> sectionsIndexes = getSectionsIndexes(sectionNamesRow);

        ArrayList<Student> students = new ArrayList<>();

        String[] studentRow;
        while ((studentRow = reader.readNext()) != null) {
            if (studentRow[0].split(" ").length == 2){
                students.add(getStudent(studentRow, maxScoreRow, sectionNamesRow, taskNamesRow, sectionsIndexes));
            }
        }
        return students;
    }

    private Student getStudent(String[] studentRow, String[] maxScoreRow, String[] sectionNamesRow, String[] taskNamesRow, ArrayList<Integer> sectionIndexes) {
        String surname = studentRow[nameIndex].split(" ")[0];
        String name = studentRow[nameIndex].split(" ")[1];
        UUID ulearnId = UUID.fromString(studentRow[uuidIndex]);
        String email = studentRow[emailIndex];
        String group = studentRow[groupIndex];
        StudentGrade studentGrade = getStudentGrade(studentRow, maxScoreRow, sectionNamesRow, taskNamesRow, sectionIndexes, ulearnId);
        return new Student(surname, name, ulearnId, email, group, studentGrade);
    }

    private StudentGrade getStudentGrade(String[] studentRow, String[] maxScoreRow, String[] sectionNamesRow, String[] taskNamesRow, ArrayList<Integer> sectionIndexes, UUID ulearnId) {
        int maxActivitiesScore = Integer.parseInt(maxScoreRow[4]);
        int maxExerciseScore = Integer.parseInt(maxScoreRow[5]);
        int maxPractiseScore = Integer.parseInt(maxScoreRow[6]);
        int maxSeminarScore = Integer.parseInt(maxScoreRow[7]);
        int totalActivitiesScore = Integer.parseInt(studentRow[4]);
        int totalExerciseScore = Integer.parseInt(studentRow[5]);
        int totalPractiseScore = Integer.parseInt(studentRow[6]);
        int totalSeminarScore = Integer.parseInt(studentRow[7]);
        ArrayList<Section> sections = getSections(sectionIndexes, sectionNamesRow, ulearnId, maxScoreRow, studentRow, taskNamesRow);
        return new StudentGrade(ulearnId, maxActivitiesScore, maxExerciseScore, maxPractiseScore, maxSeminarScore, totalActivitiesScore, totalExerciseScore, totalPractiseScore, totalSeminarScore, sections);
    }

    private ArrayList<Section> getSections(ArrayList<Integer> sectionIndexes, String[] sectionNamesRow, UUID ulearnId, String[] maxScoreRow, String[] studentRow, String[] taskNamesRow) {
        ArrayList<Section> sections = new ArrayList<>();
        for (int i = 0; i < sectionIndexes.size(); i++) {
            if (sectionIndexes.size() - i > 1) {
                sections.add(getSection(sectionNamesRow, sectionIndexes.get(i), sectionIndexes.get(i+1), ulearnId, maxScoreRow, studentRow, taskNamesRow));
            } else {
                sections.add(getSection(sectionNamesRow, sectionIndexes.get(i), 199, ulearnId, maxScoreRow, studentRow, taskNamesRow));
            }
        }
        return sections;
    }

    private Section getSection(String[] sectionNamesRow, int sectionStartIndex, int sectionEndIndex, UUID ulearnId, String[] maxScoreRow, String[] studentRow, String[] taskNamesRow) {
        ArrayList<Task> tasks = getTasks(ulearnId, maxScoreRow, studentRow, taskNamesRow, sectionStartIndex, sectionEndIndex);
        int countExerciseTasks = 0;
        int countPractiseTasks = 0;
        for (Task task: tasks) {
            if (task.getTaskType() == TaskType.Exercise) {
                countExerciseTasks++;
            } else if (task.getTaskType() == TaskType.Practice) {
                countPractiseTasks++;
            }
        }
        String sectionName = sectionNamesRow[sectionStartIndex];
        UUID sectionId = UUID.randomUUID();
        int maxExerciseScore = Integer.parseInt(maxScoreRow[sectionStartIndex + 1]);
        int maxPracticeScore = Integer.parseInt(maxScoreRow[sectionStartIndex + 1 + countExerciseTasks]);
        int maxSeminarScore = Integer.parseInt(maxScoreRow[sectionStartIndex + 1 + countExerciseTasks + countPractiseTasks]);
        return new Section(sectionId, ulearnId, maxExerciseScore, maxPracticeScore, maxSeminarScore, tasks, sectionName);
    }

    private ArrayList<Task> getTasks(UUID ulearnId, String[] maxScoreRow, String[] studentRow, String[] taskNamesRow, int sectionStartIndex, int sectionEndIndex) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int taskIndex = sectionStartIndex; taskIndex < sectionEndIndex; taskIndex++) {
            tasks.add(getTask(ulearnId, taskIndex, maxScoreRow, studentRow, taskNamesRow));
        }
        return tasks;
    }

    private Task getTask(UUID ulearnId, int taskIndex, String[] maxScoreRow, String[] studentRow, String[] taskNamesRow) {
        TaskType taskType = null;
        String taskName = taskNamesRow[taskIndex];
        if (taskName.startsWith("Упр")) {
            taskType = TaskType.Exercise;
        } else if (taskName.startsWith("ДЗ")) {
            taskType = TaskType.Practice;
        } else if (taskName.startsWith("Сем")) {
            taskType = TaskType.Seminar;
        }
        int maxScore = Integer.parseInt(maxScoreRow[taskIndex]);
        int studentScore = Integer.parseInt(studentRow[taskIndex]);
        UUID taskId = UUID.randomUUID();
        return new Task(ulearnId, taskType, maxScore, studentScore, taskName, taskId);
    }

    public ArrayList<Integer> getSectionsIndexes(String[] sectionNamesRow) {
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < sectionNamesRow.length; i++) {
            if (!sectionNamesRow[i].isBlank()) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    private CSVReader getReader() throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        FileReader fileReader = new FileReader(this.filename, Charset.forName("Windows-1251"));
        return new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
    }
}
