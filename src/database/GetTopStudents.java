package database;

import java.sql.*;
import java.util.ArrayList;

public class GetTopStudents {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public GetTopStudents(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public ArrayList<TopStudent> getTopStudents() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        String getTopExerciseQuery = "SELECT " +
                "s.surname as surname, s.name as name, sg.total_exercise_score as score_ex, " +
                "sg.total_practice_score as score_pr " +
                "FROM public.students s " +
                "JOIN public.student_grade sg on s.ulearn_id = sg.ulearn_id " +
                "ORDER BY sg.total_practice_score, sg.total_exercise_score DESC LIMIT 10";
        PreparedStatement statement = connection.prepareStatement(getTopExerciseQuery);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<TopStudent> results = new ArrayList<>();
        while (resultSet.next()) {
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            int scoreEx = resultSet.getInt("score_ex");
            int scorePr = resultSet.getInt("score_pr");
            results.add(new TopStudent(surname, name, scoreEx, scorePr));
        }
        return results;
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
