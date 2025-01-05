package com.work_block.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubNotifyService extends GithubService{
    @Autowired
    private RestTemplate restTemplate;

    public String getNotifications(String accessToken) {
        String url = "https://api.github.com/notifications?all=true"; // Lấy tất cả thông báo

        // Tạo header Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Gửi request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Failed to get notifications: " + e.getMessage();
        }
    }
}
