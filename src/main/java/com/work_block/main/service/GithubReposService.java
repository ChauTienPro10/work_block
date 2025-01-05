package com.work_block.main.service;

import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.github.GithubReposData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubReposService extends GithubService {

    @Autowired
    private RestTemplate restTemplate;

    public List<GithubReposData> getUserRepositories(String accessToken) {
        String url = "https://api.github.com/user/repos";

        // Tạo header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // Tạo HttpEntity với headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Sử dụng ParameterizedTypeReference để ánh xạ danh sách
        ResponseEntity<List<GithubReposData>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<GithubReposData>>() {
                }
        );

        return response.getBody();
    }
}