package vkAPI;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetUserById {
    private final int userId;
    private final String accessToken;

    public GetUserById(int userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public JsonNode getUser() throws IOException {
        String getUserUrl = "https://api.vk.com/method/users.get";
        String fields = "bdate,city,country,education,last_seen,online,schools,sex,universities";
        String postData = "user_ids=" + this.userId + "&lang=ru" +
                "&fields=" + fields + "&v=5.199&access_token=" + this.accessToken;
        String response = sendPostRequest(getUserUrl, postData);
        return getUserInfo(response);
    }

    private String sendPostRequest(String url, String postData) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData.getBytes(StandardCharsets.UTF_8));
            wr.flush();
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream())))) {
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    private JsonNode getUserInfo(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.path("response").path(0);
    }
}
