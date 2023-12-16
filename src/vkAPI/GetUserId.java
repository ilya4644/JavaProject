package vkAPI;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.net.URL;

public class GetUserId {
    private final String[] groupIds;
    private final String accessToken;

    public GetUserId(String[] groupIds, String accessToken) {
        this.groupIds = groupIds;
        this.accessToken = accessToken;
    }

    public ArrayList<Integer> getUsers() throws IOException {
        String getMembersUrl = "https://api.vk.com/method/groups.getMembers";
        ArrayList<Integer> membersIds = new ArrayList<>();
        for (String groupId: groupIds) {
            String postData = "group_id=" + groupId + "&v=5.199&access_token=" + accessToken;
            String response = sendPostRequest(getMembersUrl, postData);
            JsonNode listIds = getIds(response);
            if (listIds.isArray()) for (JsonNode id : listIds) membersIds.add(id.getValueAsInt());
        }
        return membersIds;
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

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    private JsonNode getIds(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.path("response").path("items");
    }
}
