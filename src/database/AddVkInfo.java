package database;

import org.codehaus.jackson.JsonNode;

import java.sql.*;
import java.text.SimpleDateFormat;

public class AddVkInfo {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public AddVkInfo(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public void addInfo(JsonNode userInfo) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        String addInfoQuery = "UPDATE " +
                "students " +
                "SET vk_id = ?, sex = ?, school = ?, education = ?, " +
                "faculty_name = ?, birth_date = ?, country = ?, city = ? " +
                "WHERE name = ? AND  surname = ?";
        PreparedStatement statement = connection.prepareStatement(addInfoQuery);
        int vkId = userInfo.path("id").getValueAsInt();
        String userName = userInfo.path("first_name").getTextValue();
        String userSurname = userInfo.path("last_name").getTextValue();

        statement.setInt(1, vkId);
        statement.setInt(2, getSex(userInfo));
        statement.setString(3, getSchool(userInfo));
        statement.setString(4, getEducation(userInfo));
        statement.setString(5, getFacultyName(userInfo));
        statement.setDate(6, getBirthDate(userInfo));
        statement.setString(7, getCountry(userInfo));
        statement.setString(8, getCity(userInfo));

        statement.setString(9, userName);
        statement.setString(10, userSurname);

        statement.executeUpdate();
        connection.close();
    }

    private int getSex(JsonNode userInfo) {
        try {
            JsonNode sex = userInfo.path("sex");
            return sex.getValueAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    private String getSchool(JsonNode userInfo) {
        try {
            JsonNode schools = userInfo.path("schools");
            StringBuilder schoolResult = new StringBuilder();
            for (JsonNode school: schools) {
                schoolResult.append(school.path("name").getTextValue());
                schoolResult.append(", ");
            }
            schoolResult.delete(schoolResult.lastIndexOf(","), schoolResult.length() - 1);
            return schoolResult.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String getEducation(JsonNode userInfo) {
        try {
            JsonNode educations = userInfo.path("universities");
            StringBuilder educationResult = new StringBuilder();
            for (JsonNode education: educations) {
                educationResult.append(education.path("name").getTextValue());
                educationResult.append(", ");
            }
            return educationResult.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String getFacultyName(JsonNode userInfo) {
        try {
            JsonNode facultyNames = userInfo.path("universities");
            StringBuilder educationResult = new StringBuilder();
            for (JsonNode facultyName: facultyNames) {
                educationResult.append(facultyName.path("faculty_name").getTextValue());
                educationResult.append(", ");
            }
            return educationResult.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private Date getBirthDate(JsonNode userInfo) {
        try {
            JsonNode bdate = userInfo.path("bdate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date parsedDate = dateFormat.parse(bdate.getTextValue());
            return new Date(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private String getCountry(JsonNode userInfo) {
        try {
            JsonNode country = userInfo.path("country").path("title");
            return country.getTextValue();
        } catch (Exception e) {
            return null;
        }
    }

    private String getCity(JsonNode userInfo) {
        try {
            JsonNode city = userInfo.path("city").path("title");
            return city.getTextValue();
        } catch (Exception e) {
            return null;
        }
    }
}
