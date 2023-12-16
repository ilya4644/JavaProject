package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GetCountryAndCount {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public GetCountryAndCount(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public ArrayList<GetCounter> getCountryAndCount() throws SQLException {
        ArrayList<GetCounter> countryCountList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(this.databaseUrl, this.user, this.password);
        String getCountryAndCountQuery = "SELECT country, COUNT(country) AS country_count\n" +
                "FROM students\n" +
                "WHERE country IS NOT NULL\n" +
                "GROUP BY country\n" +
                "ORDER BY country_count DESC\n" +
                "LIMIT 20;\n";
        PreparedStatement statement = connection.prepareStatement(getCountryAndCountQuery);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
            String country = resultSet.getString("country");
            int count = resultSet.getInt("country_count");
            countryCountList.add(new GetCounter(country, count));
        }
        Comparator<GetCounter> comparator = Comparator.comparingInt(GetCounter::getCount).reversed();
        countryCountList.sort(comparator);
        return countryCountList;
    }
}
