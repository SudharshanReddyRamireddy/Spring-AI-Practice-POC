package MyChatBot.ChatBotService.ServiceLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIConnectionService {
	
	@Value("${spring.ai.ollama.base-url}")  // Get base URL from your config
    private String aiBaseUrl;
	
	
	
	
	public String getAiResponse(String userMessage) {
        try {
            // Construct the request body using a Map to ensure proper JSON formatting
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "mistral");

            // Create the messages list
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", userMessage);
            messages.add(message);

            requestBody.put("messages", messages);

            // Convert the Map to a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Call the AI service
            RestTemplate restTemplate = new RestTemplate();
            String url = aiBaseUrl + "/v1/chat/completions"; // Ensure this is the correct endpoint

            // Set headers (if needed)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            
            // Perform the POST request
            String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

	
	
	
}
