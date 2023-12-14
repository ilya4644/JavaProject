import com.opencsv.exceptions.CsvValidationException;
import database.*;
import entities.Student;
import getEntities.Parser;
import graphics.MeanCourseDonePie;
import graphics.TopStudentsBar;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, SQLException {
//        Parse from file
        String filename = "C:\\Users\\ilya4\\IdeaProjects\\project\\src\\csv\\basicprogramming.csv";
        Parser parser = new Parser(filename);
        ArrayList<Student> students = parser.parseStudents();

//        Database
        String databaseUrl = "jdbc:postgresql://127.0.0.1:5432/akhmeto_vi";
        String name = "akhmeto_vi";
        String password = "1";

//        Create DB tables
        CreateTables creator = new CreateTables(databaseUrl, name, password);
        creator.createTables();

//        Write to DB
        SaveStudents saver = new SaveStudents(students, databaseUrl, name, password);
        saver.saveToDB();
        float[] values = new GetMeanCourseDone(databaseUrl, name, password).getPercentCourseDone();
        String[] names = {"Mean exercise done", "Mean practice done"};
        SwingUtilities.invokeLater(() -> {
            MeanCourseDonePie meanCourseDonePie = new MeanCourseDonePie("Среднее выполнение заданий по отношению друг к другу", values, names);
            meanCourseDonePie.setSize(800, 600);
            meanCourseDonePie.setLocationRelativeTo(null);
            meanCourseDonePie.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            meanCourseDonePie.setVisible(true);
        });
        ArrayList<TopStudent> topStudents = new GetTopStudents(databaseUrl, name, password).getTopStudents();
        SwingUtilities.invokeLater(() -> {
            TopStudentsBar topStudentsBar = new TopStudentsBar("Top 10 students", topStudents);
            topStudentsBar.setSize(800, 600);
            topStudentsBar.setLocationRelativeTo(null);
            topStudentsBar.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            topStudentsBar.setVisible(true);
        });
    }
}

