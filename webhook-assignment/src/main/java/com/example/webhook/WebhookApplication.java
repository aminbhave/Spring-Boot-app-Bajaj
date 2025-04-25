// Spring Boot main class
/* File: src/main/java/com/example/webhook/WebhookApplication.java */

package com.example.webhook;

import com.example.webhook.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class WebhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook";

            // Step 1: Send Request to /generateWebhook
            Map<String, String> request = new HashMap<>();
            request.put("name", "John Doe");
            request.put("regNo", "REG12347");
            request.put("email", "john@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                    generateWebhookUrl, HttpMethod.POST, entity, WebhookResponse.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.out.println("Failed to get webhook.");
                return;
            }

            WebhookResponse body = response.getBody();
            String webhookUrl = body.getWebhook();
            String accessToken = body.getAccessToken();
            List<User> users = body.getData().getUsers();

            // Step 2: Solve the problem (Mutual Followers)
            List<List<Integer>> outcome = new ArrayList<>();
            Map<Integer, Set<Integer>> followMap = new HashMap<>();

            for (User u : users) {
                followMap.put(u.getId(), new HashSet<>(u.getFollows()));
            }

            Set<String> visitedPairs = new HashSet<>();

            for (User u : users) {
                for (int followeeId : u.getFollows()) {
                    if (followMap.containsKey(followeeId) && followMap.get(followeeId).contains(u.getId())) {
                        int min = Math.min(u.getId(), followeeId);
                        int max = Math.max(u.getId(), followeeId);
                        String pairKey = min + ":" + max;
                        if (!visitedPairs.contains(pairKey)) {
                            outcome.add(Arrays.asList(min, max));
                            visitedPairs.add(pairKey);
                        }
                    }
                }
            }

            // Step 3: Send output to webhook with Authorization
            Map<String, Object> output = new HashMap<>();
            output.put("regNo", "REG12347");
            output.put("outcome", outcome);

            HttpHeaders outHeaders = new HttpHeaders();
            outHeaders.setContentType(MediaType.APPLICATION_JSON);
            outHeaders.set("Authorization", accessToken);

            HttpEntity<Map<String, Object>> outEntity = new HttpEntity<>(output, outHeaders);

            int attempts = 0;
            while (attempts < 4) {
                try {
                    ResponseEntity<String> outResponse = restTemplate.exchange(webhookUrl, HttpMethod.POST, outEntity, String.class);
                    if (outResponse.getStatusCode().is2xxSuccessful()) {
                        System.out.println("Posted outcome successfully!");
                        break;
                    } else {
                        attempts++;
                        System.out.println("Attempt " + attempts + " failed: " + outResponse.getStatusCode());
                    }
                } catch (Exception e) {
                    attempts++;
                    System.out.println("Attempt " + attempts + " failed: " + e.getMessage());
                }
            }
        };
    }
}
