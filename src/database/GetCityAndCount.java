package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GetCityAndCount {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public GetCityAndCount(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public ArrayList<GetCounter> getCityAndCount() throws SQLException {
        ArrayList<GetCounter> cityCountList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(this.databaseUrl, this.user, this.password);
        String getCityAndCountQuery = "SELECT city, COUNT(city) AS city_count\n" +
                "FROM students\n" +
                "WHERE city IS NOT NULL\n" +
                "GROUP BY city\n" +
                "ORDER BY city_count DESC\n" +
                "LIMIT 20;\n";
        PreparedStatement statement = connection.prepareStatement(getCityAndCountQuery);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
            String city = resultSet.getString("city");
            int count = resultSet.getInt("city_count");
            cityCountList.add(new GetCounter(city, count));
        }
        Comparator<GetCounter> comparator = Comparator.comparingInt(GetCounter::getCount).reversed();
        cityCountList.sort(comparator);
        return cityCountList;
    }
}
