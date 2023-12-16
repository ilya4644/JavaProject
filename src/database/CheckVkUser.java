package database;

import java.sql.*;

public class CheckVkUser {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public CheckVkUser(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public boolean checkVkUser(String firstName, String lastName) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        String checkVkUserQuery = "SELECT * FROM students WHERE name = ? AND surname = ?";
        PreparedStatement statement = connection.prepareStatement(checkVkUserQuery);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        return resultSet.next();
    }
}
