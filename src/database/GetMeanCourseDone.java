package database;

import java.sql.*;

public class GetMeanCourseDone {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public GetMeanCourseDone(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public float[] getPercentCourseDone() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        String getPercentCourseDoneQuery = "SELECT " +
                "sum(sg.total_exercise_score) as tot_ex, " +
                "sum(sg.total_practice_score) as tot_pr, " +
                "sum(sg.max_exercise_score) as max_ex, " +
                "sum(sg.max_practice_score) as max_pr " +
                "FROM student_grade sg";
        PreparedStatement statement = connection.prepareStatement(getPercentCourseDoneQuery);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int tot_ex = resultSet.getInt("tot_ex");
        int tot_pr = resultSet.getInt("tot_pr");
        int max_ex = resultSet.getInt("max_ex");
        int max_pr = resultSet.getInt("max_pr");
        float mean_ex = (float) tot_ex / max_ex;
        float mean_pr = (float) tot_pr / max_pr;
        return new float[] {mean_ex, mean_pr};
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
