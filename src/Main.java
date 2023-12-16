import com.opencsv.exceptions.CsvValidationException;
import database.*;
import entities.Student;
import getEntities.Parser;
import graphics.*;
import org.codehaus.jackson.JsonNode;
import vkAPI.GetUserById;
import vkAPI.GetUserId;

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
//
////        Create DB tables
        CreateTables creator = new CreateTables(databaseUrl, name, password);
        creator.createTables();
//
////        Write to DB
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
        String[] groups = {"basicprogrammingrtf2023"};
        String accessToken = "9b2b3d459b2b3d459b2b3d4597983d9fba99b2b9b2b3d45fe506b8f4daeb345914fea12";
        GetUserId getUserId = new GetUserId(groups, accessToken);
        ArrayList<Integer> userIds = getUserId.getUsers();
        CheckVkUser checker = new CheckVkUser(databaseUrl, name, password);
        AddVkInfo adder = new AddVkInfo(databaseUrl, name, password);
        for (Integer userId: userIds) {
            System.out.println(userId);
            JsonNode userInfo = new GetUserById(userId, accessToken).getUser();
            System.out.println(userInfo);
            String firstName = userInfo.path("first_name").getTextValue();
            String lastName = userInfo.path("last_name").getTextValue();
            System.out.println(firstName + " " + lastName);
            if (checker.checkVkUser(firstName, lastName)) {
                adder.addInfo(userInfo);
            }
        }
        ArrayList<GetCounter> cityCountList = new GetCityAndCount(databaseUrl, name, password).getCityAndCount();
        SwingUtilities.invokeLater(() -> {
            CityCountBar cityCountBar = new CityCountBar(cityCountList);
            cityCountBar.setSize(800, 600);
            cityCountBar.setLocationRelativeTo(null);
            cityCountBar.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            cityCountBar.setVisible(true);
        });
        ArrayList<GetCounter> countryCountList = new GetCountryAndCount(databaseUrl, name, password).getCountryAndCount();
        SwingUtilities.invokeLater(() -> {
            CountryCountBar countryCountBar = new CountryCountBar(countryCountList);
            countryCountBar.setSize(800, 600);
            countryCountBar.setLocationRelativeTo(null);
            countryCountBar.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            countryCountBar.setVisible(true);
        });
        ArrayList<GetCounter> sexCountList = new GetCountSex(databaseUrl, name, password).getCountSex();
        SwingUtilities.invokeLater(() -> {
            SexCountPie countSexPie = new SexCountPie(sexCountList);
            countSexPie.setSize(800, 600);
            countSexPie.setLocationRelativeTo(null);
            countSexPie.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            countSexPie.setVisible(true);
        });
    }
}

