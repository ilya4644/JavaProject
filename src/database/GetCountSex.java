package database;

import java.sql.*;
import java.util.ArrayList;

public class GetCountSex {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public GetCountSex(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public ArrayList<GetCounter> getCountSex() throws SQLException {
        ArrayList<GetCounter> sexCountList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(this.databaseUrl, this.user, this.password);
        String getCountSexQuery = "SELECT sex, count(sex) as sex_count\n" +
                "FROM students\n" +
                "WHERE sex != 0\n" +
                "GROUP BY sex";
        PreparedStatement statement = connection.prepareStatement(getCountSexQuery);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
            int sex = resultSet.getInt("sex");
            int count = resultSet.getInt("sex_count");
            String sexString = (sex == 1) ? "Female" : "Male";
            sexCountList.add(new GetCounter(sexString, count));
        }
        return sexCountList;
    }
}
