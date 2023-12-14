import com.opencsv.exceptions.CsvValidationException;
import database.CreateTables;
import database.SaveStudents;
import entities.Student;
import getEntities.Parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, SQLException {
        String filename = "C:\\Users\\ilya4\\IdeaProjects\\project\\src\\csv\\basicprogramming.csv";
        Parser parser = new Parser(filename);
        ArrayList<Student> students = parser.parseStudents();
        String databaseUrl = "jdbc:postgresql://127.0.0.1:5432/akhmeto_vi";
        String name = "akhmeto_vi";
        String password = "1";
        CreateTables creator = new CreateTables(databaseUrl, name, password);
        creator.createTables();
        SaveStudents saver = new SaveStudents(students, databaseUrl, name, password);
        saver.saveToDB();
    }
}

