import com.opencsv.exceptions.CsvValidationException;
import entities.Student;
import getEntities.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException {
        String filename = "C:\\Users\\ilya4\\IdeaProjects\\project\\src\\csv\\basicprogramming.csv";
        Parser parser = new Parser(filename);
        ArrayList<Student> students = parser.parseStudents();
        System.out.println();
    }
}

